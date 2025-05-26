package com.ai.homework.aihomeworkproject.user.controller.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class JoinRequest(
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 6, max = 30, message = "비밀번호는 6자 이상 30자 이하여야 합니다.")
    val password: String,

    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(max = 20, message = "이름은 20자 이내여야 합니다.")
    val name: String
)
