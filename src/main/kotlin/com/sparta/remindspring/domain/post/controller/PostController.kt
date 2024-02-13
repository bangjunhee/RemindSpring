package com.sparta.remindspring.domain.post.controller

import com.sparta.remindspring.domain.post.dto.DetailedPostResponse
import com.sparta.remindspring.domain.post.dto.PostRequest
import com.sparta.remindspring.domain.post.dto.SimplePostResponse
import com.sparta.remindspring.domain.post.model.Post
import com.sparta.remindspring.domain.post.service.PostService
import com.sparta.remindspring.infra.common.SortOrder
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    @GetMapping("/paged")
    fun getAllPostsByPaging(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "ASC") sortOrder: SortOrder
    ): ResponseEntity<Page<SimplePostResponse>>{
        val posts = postService.findAllPostsByPaging(page, size, sortOrder)
        return ResponseEntity.status(HttpStatus.OK).body(posts)
    }

    @GetMapping("/{postId}")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<DetailedPostResponse>{
        val post = postService.getPostById(postId)
        return ResponseEntity.status(HttpStatus.OK).body(post)
    }


}