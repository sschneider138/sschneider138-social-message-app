package com.backend.like.controller;

import com.backend.like.dto.PostLikeDto;
import com.backend.like.service.PostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class PostLikeController {

  private final PostLikeService postLikeService;

  @PostMapping("like-post")
  @ResponseStatus(HttpStatus.OK)
  public String likePost(@Valid @RequestBody PostLikeDto postLikeDto) {
    return postLikeService.toggleLike(postLikeDto);
  }
}
