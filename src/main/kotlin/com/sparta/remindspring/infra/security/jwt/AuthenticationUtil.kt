package com.sparta.remindspring.infra.security.jwt

import com.sparta.remindspring.infra.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object AuthenticationUtil {
    private fun getAuthenticatedUser(): UserPrincipal {
        return SecurityContextHolder.getContext().authentication.principal as UserPrincipal
    }

    fun getAuthenticatedUserId(): Long = getAuthenticatedUser().id
}
