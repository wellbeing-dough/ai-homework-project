package com.ai.homework.aihomeworkproject.user.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component

@Component
class JwtValidator {

    private val secretKey = "aslkdfjaslkdjfhalskjdfhlaskjdhflkjasdhflksajdh"

    fun validateAndExtractUserId(jwt: String): Long {
        try {
            val token = jwt.removePrefix("Bearer ").trim()
            val claims: Claims = Jwts.parser()
                .setSigningKey(secretKey.toByteArray())
                .parseClaimsJws(token)
                .body

            return claims.subject.toLong()
        } catch (e: Exception) {
            throw IllegalArgumentException("유효하지 않은 JWT입니다.", e)
        }
    }
}
