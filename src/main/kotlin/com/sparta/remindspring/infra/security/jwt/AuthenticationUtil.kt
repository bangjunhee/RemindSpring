package com.sparta.remindspring.infra.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object AuthenticationUtil {
    private fun getAuthenticatedUser(): UserDetails =
        SecurityContextHolder.getContext().authentication.principal as UserDetails

    fun getAuthenticatedUserId() = getAuthenticatedUser().username.toLong()
}