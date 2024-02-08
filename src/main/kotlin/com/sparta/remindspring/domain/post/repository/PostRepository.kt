package com.sparta.remindspring.domain.post.repository

import com.sparta.remindspring.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
}