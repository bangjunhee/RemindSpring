package com.sparta.remindspring.domain.comment.repository

import com.sparta.remindspring.domain.comment.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByPostId(postId: Long, pageable: Pageable): Page<Comment>

    fun findByMemberIdAndPostId(memberId: Long, postId: Long): List<Comment>
}
