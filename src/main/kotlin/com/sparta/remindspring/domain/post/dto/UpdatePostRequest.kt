package com.sparta.remindspring.domain.post.dto

import jakarta.validation.constraints.NotBlank

data class UpdatePostRequest(

    @field:NotBlank(message = "수정할 제목을 입력해주세요")
    val title: String,
    @field:NotBlank(message = "수정할 내용을 입력해주세요")
    val content: String
)
