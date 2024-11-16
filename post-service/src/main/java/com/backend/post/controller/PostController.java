package com.backend.post.controller;

import com.backend.post.dto.*;
import com.backend.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public List<PostResponseDto> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/all/paginated")
  @ResponseStatus(HttpStatus.OK)
  public PageDto<PostResponseDto> getAllPaginatedPosts(
      @RequestBody PaginationRequestDto paginationRequestDto) {
    int currentPage = Math.max(paginationRequestDto.pageIndex(), 0);
    int currentLimit = (paginationRequestDto.itemsPerPage() > 0) ? paginationRequestDto.itemsPerPage() : 10;
    return postService.getAllPaginatedPosts(currentPage, currentLimit);
  }

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponseDto createPost(
      @Valid @RequestBody PostCreationDto postCreationDto,
      @RequestHeader("Authorization") String authorizationHeader
  ) {
    return postService.createPost(postCreationDto, authorizationHeader);
  }

  @PatchMapping("/update/{postUUID}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public PostResponseDto updatePostContent(
      @RequestParam String postUUID,
      @RequestBody PostContentUpdateDto postContentUpdateDto,
      @RequestHeader("Authorization") String authorizationHeader
  ) {
    return postService.updatePostContent(postUUID, postContentUpdateDto, authorizationHeader);
  }
}
