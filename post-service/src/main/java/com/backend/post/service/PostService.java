package com.backend.post.service;

import com.backend.post.client.UserClient;
import com.backend.post.client.UserResponseDto;
import com.backend.post.dto.PageDto;
import com.backend.post.dto.PostContentUpdateDto;
import com.backend.post.dto.PostCreationDto;
import com.backend.post.dto.PostResponseDto;
import com.backend.post.model.Post;
import com.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final UserClient userClient;

  private final String invalidUserInfo = "invalid user information: user could not be validated ";
  private final String postNotFoundError = "post not found for uuid: ";
  private final String unauthorizedAccessError = "unauthorized: you are not the author of this post";

  public List<PostResponseDto> getAllPosts() {
    return postRepository.findAll().stream()
        .map(post -> mapPostToDto(post))
        .collect(Collectors.toList());
  }


  public PageDto<PostResponseDto> getAllPaginatedPosts(int pageIndex, int itemsPerPage) {
    PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
    Page<Post> page = postRepository.findAll(pageRequest);

    Page<PostResponseDto> dtoPage = page.map(post -> mapPostToDto(post));

    return new PageDto<>(
        dtoPage.getContent(),
        dtoPage.getTotalPages(),
        (int) dtoPage.getTotalElements(),
        dtoPage.getNumber(),
        dtoPage.getSize());
  }


  // requires authentication and authorization
  public PostResponseDto createPost(PostCreationDto postCreationDto, String authorizationHeader) {
    UserResponseDto user = validateAndGetUser(authorizationHeader);

    if (user == null || user.userUUID() == null || user.username() == null) {
      throw new RuntimeException(invalidUserInfo);
    }

    Post post = Post.builder()
        .authorUUID(user.userUUID())
        .authorUsername(user.username())
        .postContent(postCreationDto.postContent())
        .tags(postCreationDto.tags())
        .build();

    Post savedPost = postRepository.save(post);
    return mapPostToDto(savedPost);
  }


  // requires authentication and authorization
  public PostResponseDto updatePostContent(
      String postUUID, PostContentUpdateDto postContentUpdateDto, String authorizationHeader) {

    UserResponseDto user = validateAndGetUser(authorizationHeader);

    if (user == null || user.userUUID() == null || user.username() == null) {
      throw new RuntimeException(invalidUserInfo);
    }

    Post post = postRepository.findByPostUUID(postUUID)
        .orElseThrow(() -> new RuntimeException(postNotFoundError + postUUID));

    if (!post.getAuthorUUID().equals(postContentUpdateDto.userUUID())) {
      throw new RuntimeException(unauthorizedAccessError);
    }

    if (postContentUpdateDto.postContent() != null) {
      post.setPostContent(postContentUpdateDto.postContent());
    }

    Post savedPost = postRepository.save(post);
    return mapPostToDto(savedPost);
  }


  private UserResponseDto validateAndGetUser(String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException(unauthorizedAccessError);
    }

    String token = authorizationHeader.substring(7);

    try {
      return userClient.validateUser("Bearer " + token);
    } catch (Exception e) {
      throw new RuntimeException(unauthorizedAccessError, e);
    }
  }


  private PostResponseDto mapPostToDto(Post post) {
    return new PostResponseDto(
        post.getPostUUID(),
        post.getAuthorUUID(),
        post.getAuthorUsername(),
        post.getPostContent(),
        post.getDatePosted(),
        post.getShareCount(),
        post.getTags());
  }
}
