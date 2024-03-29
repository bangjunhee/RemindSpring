package com.sparta.remindspring.domain.post.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SimplePostResponse(
    val title: String,
    val author: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime
)
