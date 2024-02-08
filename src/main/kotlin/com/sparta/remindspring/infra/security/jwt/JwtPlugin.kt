package com.sparta.remindspring.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long
) {
    // 토큰이 유효한지 검사하는 메서드
    // jwt 를 받아 runCatching 을 사용해 Result 형태로 반환
    // secret 을 이용해 key 생성, key 를 기반으로 검증 수행
    fun validateToken(jwt : String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims((jwt))
        }
    }


    fun generateAccessToken(subject: String, nickname: String): String {
        return generateToken(subject, nickname, Duration.ofHours(accessTokenExpirationHour))
    }

    private fun generateToken(subject: String, nickname: String, expirationPeriod: Duration): String {
        // CustomClaims 설정
        val claims: Claims = Jwts.claims()
            .add(mapOf("nickname" to nickname))
            .build()


        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}