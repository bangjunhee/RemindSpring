package com.sparta.remindspring.domain.comment.controller

import com.sparta.remindspring.domain.comment.dto.CommentRequest
import com.sparta.remindspring.domain.comment.dto.CommentResponse
import com.sparta.remindspring.domain.comment.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/{postId}")
    fun creatComment(
        @PathVariable postId: Long,
        @Valid @RequestBody commentRequest: CommentRequest,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, commentRequest))
    }

    @PatchMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @Valid @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse>{
        val comment = commentService.updateComment(commentId, commentRequest)
        return ResponseEntity.ok(comment)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long): ResponseEntity<Unit>{
        commentService.deleteComment(commentId)
        return ResponseEntity.noContent().build()
    }

}