package com.ai.homework.aihomeworkproject.chat.service

import ChatMessage
import OpenAiRequest
import com.ai.homework.aihomeworkproject.chat.controller.dto.response.ChatResponse
import com.ai.homework.aihomeworkproject.chat.controller.dto.response.ChatThreadListResponse
import com.ai.homework.aihomeworkproject.chat.controller.dto.response.ChatThreadResponse
import com.ai.homework.aihomeworkproject.chat.domain.entity.Chat
import com.ai.homework.aihomeworkproject.chat.repository.ChatQueryDslRepository
import com.ai.homework.aihomeworkproject.chat.repository.ChatRepository
import com.ai.homework.aihomeworkproject.client.OpenAiClient
import com.ai.homework.aihomeworkproject.thread.domain.entity.Thread
import com.ai.homework.aihomeworkproject.thread.repository.ThreadRepository
import com.ai.homework.aihomeworkproject.user.repository.UserRepository
import com.ai.homework.aihomeworkproject.user.service.JwtValidator
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class ChatService(
    private val openAiClient: OpenAiClient,
    private val jwtValidator: JwtValidator,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val threadRepository: ThreadRepository,
    private val chatQueryDslRepository: ChatQueryDslRepository
) {
    fun chat(jwt: String, message: String, model: String, isStreaming: Boolean): ChatResponse {
        val userId = jwtValidator.validateAndExtractUserId(jwt)
        userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("사용자를 찾을 수 없습니다. ID: $userId")
        }

        val now = LocalDateTime.now()
        val latestChat = chatRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)

        // 기본값: 새 스레드 생성 필요
        var threadIdToUse: Long? = null
        var threadChats: List<Chat> = emptyList()

        if (latestChat?.createdAt?.plusMinutes(30)?.isAfter(now) == true) {
            // 30분 이내 → 기존 스레드 사용
            val latestThreadId = threadRepository.findByChatId(latestChat.id)?.id
            if (latestThreadId != null) {
                threadIdToUse = latestThreadId
                threadChats = chatQueryDslRepository.findAllByThreadIdOrderByCreatedAtAsc(threadIdToUse)
            }
        }

        // 새로운 Chat 저장 (답변 전 상태)
        val newChat = Chat(
            userId = userId,
            question = message,
            answer = null,
            createdAt = now
        )
        chatRepository.save(newChat)

        // 새 스레드 생성 시점
        if (threadIdToUse == null) {
            val thread = Thread(
                userId = userId,
                chatId = newChat.id,
                createdAt = now
            )
            threadRepository.save(thread)
            threadIdToUse = thread.id
        }

        val messagesForOpenAi: List<ChatMessage> = if (threadChats.isNotEmpty()) {
            threadChats.flatMap { chat ->
                listOf(
                    ChatMessage(role = "user", content = chat.question!!),
                    ChatMessage(role = "assistant", content = chat.answer!!)
                )
            } + listOf(ChatMessage(role = "user", content = message))
        } else {
            listOf(ChatMessage(role = "user", content = message))
        }


        // 🔌 OpenAI 요청
        val openAiResponse = openAiClient.generateResponse(
            authorization = "Bearer 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",
            request = OpenAiRequest(
                model = "gpt-3.5-turbo",
                messages = messagesForOpenAi
            )
        )
        println("🟢 OpenAI 응답 객체: $openAiResponse")


        val replyText = openAiResponse.choices
            ?.firstOrNull()
            ?.message
            ?.content
            ?: "답변을 가져올 수 없습니다."

        newChat.answer = replyText
        chatRepository.save(newChat)

        return ChatResponse(reply = replyText)
    }

    fun getChatThreads(jwt: String, page: Int, size: Int, sortAsc: Boolean): ChatThreadListResponse {
        val userId = jwtValidator.validateAndExtractUserId(jwt)
        userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("사용자를 찾을 수 없습니다. ID: $userId")
        }

        val sort = if (sortAsc) Sort.by("createdAt").ascending() else Sort.by("createdAt").descending()
        val pageable = PageRequest.of(page, size, sort)

        val threads = threadRepository.findAllByUserId(userId, pageable)

        val content = threads.map { thread ->
            val chats = chatQueryDslRepository.findAllByThreadIdOrderByCreatedAtAsc(thread.id)
            ChatThreadResponse(
                threadId = thread.id,
                createdAt = thread.createdAt,
                chats.map { ChatResponse("${it.question ?: ""} / ${it.answer ?: ""}") }
            )
        }

        return ChatThreadListResponse(
            totalPages = threads.totalPages,
            totalElements = threads.totalElements,
            currentPage = threads.number,
            threads = content.toList()
        )
    }

}