package com.sparta.remindspring.domain.post.model

import com.sparta.remindspring.domain.comment.dto.CommentResponse
import com.sparta.remindspring.domain.member.model.Member
import com.sparta.remindspring.domain.post.dto.DetailedPostResponse
import com.sparta.remindspring.domain.post.dto.PostRequest
import com.sparta.remindspring.domain.post.dto.SimplePostResponse
import com.sparta.remindspring.domain.post.dto.UpdatePostRequest
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.security.access.AccessDeniedException
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title")
    var title: String,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id")
    val member: Member,

    @Column(columnDefinition = "TIMESTAMP(6)", name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "content")
    var content: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    val id: Long ?= null

    fun fromSimple() = SimplePostResponse(
        title = title,
        author = member.nickname,
        createdAt = createdAt
    )

    fun fromDetailed(commentList: List<CommentResponse> = emptyList()) = DetailedPostResponse(
        title = title,
        author = member.nickname,
        createdAt = createdAt,
        content = content,
        commentList = commentList
    )

    fun updatePost(request: UpdatePostRequest, authenticationId: Long) {
        if (verify(authenticationId)) {
            title = request.title
            content = request.content
            return
        }
    }

    fun verify(authenticationId: Long): Boolean {
        if (this.member.id == authenticationId) {
            return true
        }

        throw AccessDeniedException("Verify Failed")
    }

    companion object {
        fun of(request: PostRequest, member: Member) = Post(
            title = request.title,
            member = member,
            content = request.content
        )
    }
}