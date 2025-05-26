package com.ai.homework.aihomeworkproject.client

data class GeminiResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String,
    val responseId: String
)

data class Candidate(
    val content: CandidateContent,
    val finishReason: String,
    val avgLogprobs: Double
)

data class CandidateContent(
    val parts: List<Part>,
    val role: String
)

data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int,
    val promptTokensDetails: List<TokensDetail>,
    val candidatesTokensDetails: List<TokensDetail>
)

data class TokensDetail(
    val modality: String,
    val tokenCount: Int
)
