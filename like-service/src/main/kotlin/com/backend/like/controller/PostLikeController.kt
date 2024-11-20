package com.backend.like.controller

import com.backend.like.dto.PostLikeDto
import com.backend.like.service.PostLikeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/like")
class PostLikeController(private val postLikeService: PostLikeService) {

    @PostMapping("like-post")
    @ResponseStatus(HttpStatus.OK)
    fun likePost(@Valid @RequestBody postLikeDto: PostLikeDto): String {
        return postLikeService.toggleLike(postLikeDto)
    }
}