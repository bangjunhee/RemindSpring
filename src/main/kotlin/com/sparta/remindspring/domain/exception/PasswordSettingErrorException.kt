package com.sparta.remindspring.domain.exception

data class PasswordSettingErrorException (
    override val message: String? = "비밀번호 설정을 다시 해 주세요!"
): RuntimeException()