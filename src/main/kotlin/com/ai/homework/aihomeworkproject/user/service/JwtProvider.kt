package com.ai.homework.aihomeworkproject.user.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider {

    private val secretKey = "aslkdfjaslkdjfhalskjdfhlaskjdhflkjasdhflksajdh"
    private val validityInMilliseconds: Long = 3600000

    fun generateToken(userId: Long): String {
        val claims = Jwts.claims().setSubject(userId.toString())
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
            .compact()
    }
}
