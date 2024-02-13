package com.sparta.remindspring.domain.comment.service

import com.sparta.remindspring.domain.comment.dto.CommentRequest
import com.sparta.remindspring.domain.comment.dto.CommentResponse
import com.sparta.remindspring.domain.comment.model.Comment
import com.sparta.remindspring.domain.comment.repository.CommentRepository
import com.sparta.remindspring.domain.exception.ModelNotFoundException
import com.sparta.remindspring.domain.member.repository.MemberRepository
import com.sparta.remindspring.domain.post.repository.PostRepository
import com.sparta.remindspring.infra.security.jwt.AuthenticationUtil.getAuthenticatedUserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun createComment(postId: Long, request: CommentRequest): CommentResponse {
        val authenticatedId = getAuthenticatedUserId()
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Comment", null)
        val member = memberRepository.findByIdOrNull(authenticatedId)!!
        val comment = Comment.of(request, member, post)

        commentRepository.save(comment)

        return comment.from()
    }

    @Transactional
    fun updateComment(commentId: Long, request: CommentRequest): CommentResponse{
        val authenticatedId = getAuthenticatedUserId()
        val updatedComment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", null)

        updatedComment.update(request.content, authenticatedId)

        return updatedComment.from()
    }

    @Transactional
    fun deleteComment(commentId: Long){
        val authenticatedId = getAuthenticatedUserId()
        val deletedComment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", null)

        if (deletedComment.verify(authenticatedId)){
            commentRepository.delete(deletedComment)
        }
    }
}