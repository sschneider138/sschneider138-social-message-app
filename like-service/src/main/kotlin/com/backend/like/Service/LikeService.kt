package com.backend.like.Service

import com.backend.like.model.PostLike
import com.backend.like.repository.PostLikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(private val postLikeRepository: PostLikeRepository) {
    fun addLike(postUUID: String, userUUID: String) {
        val like = PostLike(postUUID = postUUID, userUUID = userUUID)
        postLikeRepository.save(like)
    }
}
