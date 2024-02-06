package com.sparta.remindspring.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest(
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=\\S+$).{3,}$",
        message = "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하여야 합니다."
    )
    val nickname: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=\\S+$).{4,}$",
        message = "비밀번호는 최소 4자 이상이어야 하며 닉네임과 중복된 값이 포함되면 안됍니다."
    )
    val password: String,

    @field:NotBlank(message = "비밀번호를 한번 더 입력해 주세요.")
    val passwordCheck: String
)
