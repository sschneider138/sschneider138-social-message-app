package com.backend.like.service

import com.backend.like.dto.PostLikeDto
import com.backend.like.model.PostLike
import com.backend.like.repository.PostLikeRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class PostLikeService(private val postLikeRepository: PostLikeRepository) {

    @Transactional
    fun toggleLike(@Valid @RequestBody postLikeDto: PostLikeDto): String {
        val existingLike: PostLike? =
            postLikeRepository.findByPostUUIDAndUserUUID(postLikeDto.postUUID, postLikeDto.userUUID)

        val action: String

        if (existingLike != null) {
            postLikeRepository.delete(existingLike)
            action = "dislike"
        } else {
            val like = PostLike(postUUID = postLikeDto.postUUID, userUUID = postLikeDto.userUUID)
            postLikeRepository.save(like)
            action = "like"
        }

        return action
    }
}
