package com.ai.homework.aihomeworkproject.chat.controller.dto.request

data class ChatRequest(
    val message: String,
    val model: String? = null,
    val isStreaming: Boolean = false
)
