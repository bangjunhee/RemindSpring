package com.sparta.remindspring.domain.comment.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentResponse(
    val nickname: String,
    val content: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime
)
