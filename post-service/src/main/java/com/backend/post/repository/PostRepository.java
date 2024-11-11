package com.backend.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostUUID(String postUUID);
}
