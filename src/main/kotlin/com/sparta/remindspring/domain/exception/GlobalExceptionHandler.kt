package com.sparta.remindspring.domain.exception

import com.sparta.remindspring.domain.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handlePasswordSettingErrorException(e: PasswordSettingErrorException): ResponseEntity<ErrorResponse>{
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message))
    }
    @ExceptionHandler
    fun handlePasswordCheckErrorException(e: PasswordCheckErrorException): ResponseEntity<ErrorResponse>{
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message))
    }
    @ExceptionHandler
    fun handleDuplicateNicknameException(e: DuplicateNicknameException): ResponseEntity<Unit>{
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .build()
    }
    @ExceptionHandler
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMassage = "조건에 충족되지 않는 입력값입니다."
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(errorMassage))
    }
}