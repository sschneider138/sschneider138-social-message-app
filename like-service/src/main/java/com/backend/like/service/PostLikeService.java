package com.backend.like.service;

import com.backend.like.dto.PostLikeDto;
import com.backend.like.model.PostLike;
import com.backend.like.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;

  @Transactional
  public String toggleLike(@RequestBody PostLikeDto postLikeDto) {
    Optional<PostLike> optionalPostLike = postLikeRepository.findByPostUUIDAndUserUUID(postLikeDto.postUUID(), postLikeDto.userUUID());

    String action;

    if (optionalPostLike.isPresent()) {

      PostLike existingLike = PostLike.builder()
          .postUUID(postLikeDto.postUUID())
          .userUUID(postLikeDto.userUUID())
          .build();
      postLikeRepository.delete(existingLike);
      action = "dislike";

    } else {
      PostLike postLike = PostLike.builder()
          .postUUID(postLikeDto.postUUID())
          .userUUID(postLikeDto.userUUID())
          .build();
      postLikeRepository.save(postLike);
      action = "like";
    }

    return action;

  }

}
