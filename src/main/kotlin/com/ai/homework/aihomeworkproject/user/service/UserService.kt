package com.ai.homework.aihomeworkproject.user.service

import com.ai.homework.aihomeworkproject.client.*
import com.ai.homework.aihomeworkproject.user.entity.User
import com.ai.homework.aihomeworkproject.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.ai.homework.aihomeworkproject.user.entity.Role

@Service
class UserService(
    private val openAiClient: OpenAiClient,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) {


    fun join(name: String, email: String, password: String) {
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
        }

        val user = User(
            name = name,
            email = email,
            password = password,
            createdAt = LocalDateTime.now(),
            role = Role.MEMBER
        )

        userRepository.save(user)
    }

    fun login(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.") }

        if (user.password != password) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.")
        }

        return jwtProvider.generateToken(user.id)
    }



}
