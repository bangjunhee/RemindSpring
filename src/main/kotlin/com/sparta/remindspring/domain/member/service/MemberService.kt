package com.sparta.remindspring.domain.member.service

import com.sparta.remindspring.domain.exception.DuplicateNicknameException
import com.sparta.remindspring.domain.exception.PasswordCheckErrorException
import com.sparta.remindspring.domain.exception.PasswordSettingErrorException
import com.sparta.remindspring.domain.exception.dto.ModelNotFoundException
import com.sparta.remindspring.domain.member.dto.LoginRequest
import com.sparta.remindspring.domain.member.dto.LoginResponse
import com.sparta.remindspring.domain.member.dto.SignUpRequest
import com.sparta.remindspring.domain.member.model.Member
import com.sparta.remindspring.domain.member.repository.MemberRepository

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    @Transactional
    fun signUp(request: SignUpRequest): Long {
        val isContained = request.password.contains(request.nickname)
        if(request.password != request.passwordCheck){
            throw PasswordCheckErrorException()
        }
        if(isContained){
            throw PasswordSettingErrorException()
        }
        if (memberRepository.existsByNickname(request.nickname)) {
            throw DuplicateNicknameException(request.nickname)
        }
        val member = Member.of(request.copy(password = passwordEncoder.encode(request.password)))
        memberRepository.save(member)

        return member.id!!
    }

    @Transactional
    fun login(request: LoginRequest): LoginResponse{
        val member = memberRepository.findByNickname(request.nickname) ?: throw ModelNotFoundException("Member", null)

        if(!passwordEncoder.matches(request.password, member.password)){
            throw ModelNotFoundException("Member", null)
        }

        return LoginResponse(
            accessToken = jtwPlugin.generateAccessToken(

            )
        )
    }
}