package com.sparta.remindspring.domain.exception

data class PasswordCheckErrorException(
    override val message: String? = "비밀번호 확인이 일치하지 않습니다!"
): RuntimeException()
