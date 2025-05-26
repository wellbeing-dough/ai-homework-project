package com.ai.homework.aihomeworkproject.user.controller

import com.ai.homework.aihomeworkproject.client.OpenAiResponse
import com.ai.homework.aihomeworkproject.user.controller.dto.request.JoinRequest
import com.ai.homework.aihomeworkproject.user.controller.dto.request.LoginRequest
import com.ai.homework.aihomeworkproject.user.controller.dto.response.LoginResponse
import com.ai.homework.aihomeworkproject.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/v1/users/join")
    fun join(@RequestBody @Valid request: JoinRequest): ResponseEntity<HttpStatus> {
        userService.join(request.name, request.email, request.password)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/v1/users/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        val token = userService.login(request.email, request.password)
        return ResponseEntity.ok(LoginResponse(token))
    }
}
