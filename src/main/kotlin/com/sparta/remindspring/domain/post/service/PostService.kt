package com.sparta.remindspring.domain.post.service

import com.sparta.remindspring.domain.exception.ModelNotFoundException
import com.sparta.remindspring.domain.member.repository.MemberRepository
import com.sparta.remindspring.domain.post.dto.DetailedPostResponse
import com.sparta.remindspring.domain.post.dto.PostRequest
import com.sparta.remindspring.domain.post.dto.SimplePostResponse
import com.sparta.remindspring.domain.post.model.Post
import com.sparta.remindspring.domain.post.repository.PostRepository
import com.sparta.remindspring.infra.common.SortOrder
import com.sparta.remindspring.infra.security.jwt.AuthenticationUtil.getAuthenticatedUserId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createPost(request: PostRequest): DetailedPostResponse{
        val memberId = getAuthenticatedUserId()
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", null)
        val post = postRepository.save(Post.of(request, member))

        return post.fromDetailed()
    }

    fun getAllPosts(sortOrder: SortOrder): List<SimplePostResponse>{
        return when (sortOrder){
            SortOrder.ASC -> postRepository
                .findAll(Sort.by(Sort.Direction.ASC,"createdAt")).map { it.fromSimple() }
            SortOrder.DESC -> postRepository
                .findAll(Sort.by(Sort.Direction.DESC,"createdAt")).map { it.fromSimple() }
        }
    }

    fun findAllPostsByPaging(page: Int, size: Int, sortOrder: SortOrder): Page<SimplePostResponse>{
        val pageable: PageRequest = when (sortOrder){
            SortOrder.ASC -> PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"createdAt"))
            SortOrder.DESC -> PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"createdAt"))
        }
        return postRepository.findAll(pageable).map { it.fromSimple() }
    }

    fun getPostById(postId: Long): DetailedPostResponse{
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", null)

        return post.fromDetailed()
    }
}