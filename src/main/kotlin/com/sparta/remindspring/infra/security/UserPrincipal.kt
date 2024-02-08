package com.sparta.remindspring.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val nickname: String,
    val authorities: Collection<GrantedAuthority>
){
    constructor(id: Long, nickname: String, role: Set<String>): this(
        id,
        nickname,
        role.map{ SimpleGrantedAuthority("ROLE_$it") }
    )
}
