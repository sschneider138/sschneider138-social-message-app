package com.backend.like.controller

import com.backend.like.Service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/like")
class LikeController(private val likeService: LikeService) {

    @PostMapping("like-post")
    @ResponseStatus(HttpStatus.OK)
    fun likePost(@RequestParam postUUID: String, @RequestParam userUUID: String) {
        likeService.addLike(postUUID, userUUID)
    }
}