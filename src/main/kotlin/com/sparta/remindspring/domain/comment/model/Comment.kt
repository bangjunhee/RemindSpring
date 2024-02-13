package com.sparta.remindspring.domain.comment.model

import com.sparta.remindspring.domain.comment.dto.CommentRequest
import com.sparta.remindspring.domain.comment.dto.CommentResponse
import com.sparta.remindspring.domain.member.model.Member
import com.sparta.remindspring.domain.post.model.Post
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.security.access.AccessDeniedException
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "content")
    var content: String,

    @Column(columnDefinition = "TIMESTAMP(6)", name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    var member: Member,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    var post: Post
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null

    fun from(): CommentResponse{
        return CommentResponse(
            nickname = member.nickname,
            content = content,
            createdAt = createdAt
        )
    }

    fun update(newContent: String, authenticatedId: Long){
        if (verify(authenticatedId)) {
            content = newContent
            return
        }
    }
    fun verify(authenticationId: Long): Boolean {
        if (this.member.id == authenticationId) {
            return true
        }

        throw AccessDeniedException("Verify Failed")
    }

    companion object{
        fun of(request: CommentRequest, member: Member, post: Post): Comment{
            return Comment(
                content = request.content,
                member = member,
                post = post
            )
        }
    }
}