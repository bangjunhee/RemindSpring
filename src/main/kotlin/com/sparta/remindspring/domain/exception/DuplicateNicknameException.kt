package com.sparta.remindspring.domain.exception

data class DuplicateNicknameException(
    val nickname: String
) : RuntimeException("$nickname 은 이미 존재하는 닉네임입니다.")