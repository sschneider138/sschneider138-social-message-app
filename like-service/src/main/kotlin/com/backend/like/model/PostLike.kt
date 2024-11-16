package com.backend.like.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "post_likes")
class PostLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "post_uuid", nullable = false)
    val postUUID: String,

    @Column(name = "user_uuid", nullable = false)
    val userUUID: String,


    @Column(name = "liked_at_timestamp", nullable = false, updatable = false)
    val likedAt: Instant = Instant.now(),

)