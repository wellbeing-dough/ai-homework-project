package com.ai.homework.aihomeworkproject.chat.repository

import com.ai.homework.aihomeworkproject.chat.domain.entity.Chat
import com.ai.homework.aihomeworkproject.chat.domain.entity.QChat.chat
import com.ai.homework.aihomeworkproject.thread.domain.entity.QThread.thread
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ChatQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllByThreadIdOrderByCreatedAtAsc(threadId: Long): List<Chat> {
        return queryFactory
            .selectFrom(chat)
            .join(thread).on(
                thread.id.eq(threadId),
                chat.userId.eq(thread.userId),
                chat.id.goe(thread.chatId)
            )
            .orderBy(chat.createdAt.asc())
            .fetch()
    }



}