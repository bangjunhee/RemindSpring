package com.sparta.remindspring.domain.member.model

import com.sparta.remindspring.domain.member.dto.SignUpRequest
import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member (
    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password", nullable = false)
    val password: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long ?= null

    companion object{
        fun of(request: SignUpRequest) = Member(
            nickname = request.nickname,
            password = request.password
        )
    }
}