package com.sparta.remindspring.domain.member.repository

import com.sparta.remindspring.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun existsByNickname(nickname: String): Boolean

    fun findByNickname(nickname: String): Member?
}