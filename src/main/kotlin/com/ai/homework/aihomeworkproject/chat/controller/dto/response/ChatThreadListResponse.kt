package com.ai.homework.aihomeworkproject.chat.controller.dto.response

data class ChatThreadListResponse(
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int,
    val threads: List<ChatThreadResponse>
)
