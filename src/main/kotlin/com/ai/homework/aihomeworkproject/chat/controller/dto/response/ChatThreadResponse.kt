package com.ai.homework.aihomeworkproject.chat.controller.dto.response

import java.time.LocalDateTime

data class ChatThreadResponse(
    val threadId: Long,
    val createdAt: LocalDateTime,
    val chats: List<ChatResponse>
)
