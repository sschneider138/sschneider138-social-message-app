package com.backend.like.repository;

import com.backend.like.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
  Optional<PostLike> findByPostUUIDAndUserUUID(String postUUID, String userUUID);

}
