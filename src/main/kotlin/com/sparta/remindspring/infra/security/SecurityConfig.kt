package com.sparta.remindspring.infra.security


import com.sparta.remindspring.infra.security.jwt.CustomAuthenticationEntryPoint
import com.sparta.remindspring.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


// Spring Security 의 filterChain 구성 커스터마이즈 클래스
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // 사용하지 않는 필터 제외
            .httpBasic { it.disable() } // BasicAuthenticationFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 제외
            .formLogin { it.disable() } // UsernamePasswordAuthenticationFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 제외
            .csrf { it.disable() } // CsrfFilter 제외
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/v1/members/login",
                    "/api/v1/members/signup",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**"
                ).permitAll().anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .headers { it.frameOptions { it1 -> it1.disable() } }
            .build()
    }

}