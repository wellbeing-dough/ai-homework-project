package com.ai.homework.aihomeworkproject.chat.controller

import com.ai.homework.aihomeworkproject.chat.controller.dto.request.ChatRequest
import com.ai.homework.aihomeworkproject.chat.controller.dto.response.ChatResponse
import com.ai.homework.aihomeworkproject.chat.controller.dto.response.ChatThreadListResponse
import com.ai.homework.aihomeworkproject.chat.service.ChatService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping("/v1/chat")
    fun chat(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody @Valid request: ChatRequest
    ): ResponseEntity<ChatResponse> {
        val response = chatService.chat(
            jwt = authorization,
            message = request.message,
            model = "gpt-3.5-turbo",      // 고정값 사용
            isStreaming = false          // 고정값 사용
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getChatThreads(
        @RequestHeader("Authorization") jwt: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "false") sortAsc: Boolean
    ): ResponseEntity<ChatThreadListResponse> {
        val result = chatService.getChatThreads(jwt.replace("Bearer ", ""), page, size, sortAsc)
        return ResponseEntity.ok(result)
    }
}

