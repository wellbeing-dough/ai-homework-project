package com.ai.homework.aihomeworkproject.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import feign.Headers


@FeignClient(
    name = "geminiApi",
    url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash"
)
@Headers("Content-Type: application/json")
interface GeminiClient {

    @PostMapping(":generateContent")
    fun generateContent(
        @RequestParam("key") apiKey: String,
        @RequestBody request: GeminiRequest
    ): GeminiResponse
}
