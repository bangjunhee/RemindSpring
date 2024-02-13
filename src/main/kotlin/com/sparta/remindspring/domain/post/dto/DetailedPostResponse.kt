package com.sparta.remindspring.domain.post.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.sparta.remindspring.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class DetailedPostResponse (
    val title: String,
    val author: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    val content: String,
    val commentList: List<CommentResponse>,
)