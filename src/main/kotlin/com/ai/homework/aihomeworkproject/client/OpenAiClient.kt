package com.ai.homework.aihomeworkproject.client

import OpenAiRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "openAiClient", url = "https://api.openai.com/v1")
interface OpenAiClient {

    @PostMapping("/chat/completions")
    fun generateResponse(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: OpenAiRequest
    ): OpenAiResponse
}
