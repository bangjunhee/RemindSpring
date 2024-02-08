package com.sparta.remindspring.domain.post.controller

import com.sparta.remindspring.domain.post.dto.DetailedPostResponse
import com.sparta.remindspring.domain.post.dto.PostRequest
import com.sparta.remindspring.domain.post.dto.SimplePostResponse
import com.sparta.remindspring.domain.post.service.PostService
import com.sparta.remindspring.infra.common.SortOrder
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(private val postService: PostService) {

    @PostMapping
    fun createPost(
        @Valid @RequestBody postRequest: PostRequest
    ): ResponseEntity<DetailedPostResponse>{
        val createdPost = postService.createPost(postRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @GetMapping
    fun getAllPosts(
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<SimplePostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts(sortOrder))
    }


}