package com.sparta.remindspring.domain.member.controller

import com.sparta.remindspring.domain.member.dto.LoginRequest
import com.sparta.remindspring.domain.member.dto.LoginResponse
import com.sparta.remindspring.domain.member.dto.SignUpRequest

import com.sparta.remindspring.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Unit>{
        val id = memberService.signUp(signUpRequest)
        return ResponseEntity.created(URI.create(String.format("/api/v1/members/%d", id))).build()
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(loginRequest))

    }



}