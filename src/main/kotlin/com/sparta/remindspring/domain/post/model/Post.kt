package com.sparta.remindspring.domain.post.model

import com.sparta.remindspring.domain.member.model.Member
import com.sparta.remindspring.domain.post.dto.DetailedPostResponse
import com.sparta.remindspring.domain.post.dto.PostRequest
import com.sparta.remindspring.domain.post.dto.SimplePostResponse
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title")
    val title: String,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id")
    val member: Member,

    @Column(columnDefinition = "TIMESTAMP(6)", name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "content")
    val content: String

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

    fun fromDetailed() = DetailedPostResponse(
        title = title,
        author = member.nickname,
        createdAt = createdAt,
        content = content
    )

    companion object {
        fun of(request: PostRequest, member: Member) = Post(
            title = request.title,
            member = member,
            content = request.content
        )
    }
}