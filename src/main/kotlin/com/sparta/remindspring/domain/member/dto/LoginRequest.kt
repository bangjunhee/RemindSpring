package com.sparta.remindspring.domain.member.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "닉네임을 입력하세요")
    val nickname: String,

    @field:NotBlank(message = "비밀번호를 입력하세요")
    val password: String
)
