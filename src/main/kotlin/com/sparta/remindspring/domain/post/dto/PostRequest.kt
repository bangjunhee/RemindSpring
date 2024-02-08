package com.sparta.remindspring.domain.post.dto

import jakarta.validation.constraints.Size

data class PostRequest(
    @field: Size(max = 500, message = "The title cannot exceed 500 characters.")
    val title: String,

    @field: Size(max = 5000, message = "The text cannot exceed 5000 characters.")
    val content: String
)
