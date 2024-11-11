package com.backend.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.backend.post.dto.PageDto;
import com.backend.post.dto.PostContentUpdateDto;
import com.backend.post.dto.PostCreationDto;
import com.backend.post.dto.PostLikeDto;
import com.backend.post.dto.PostResponseDto;
import com.backend.post.model.Post;
import com.backend.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

// TODO: check user's uuid with user-service db to make sure user's are authenticated before liking, modifying, or creating post
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final String postNotFoundError = "post not found for uuid: ";
    private final String postNotUpdatedError = "post could not be updated for uuid: ";
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

    // TODO: require authentication
    public PostResponseDto createPost(PostCreationDto postCreationDto) {
        Post post = Post.builder()
                .authorUUID(postCreationDto.authorUUID())
                .postContent(postCreationDto.postContent())
                .tags(postCreationDto.tags())
                .build();

        Post savedPost = postRepository.save(post);
        return mapPostToDto(savedPost);
    }

    // TODO: require authentication and authorization
    public PostResponseDto updatePostContent(String postUUID,
            PostContentUpdateDto postContentUpdateDto) {
        Post post = postRepository.findByPostUUID(postUUID)
                .orElseThrow(() -> new RuntimeException(postNotFoundError + " " + postUUID));

        if (!post.getAuthorUUID().equals(postContentUpdateDto.userUUID())) {
            throw new RuntimeException(unauthorizedAccessError);
        }

        try {
            if (postContentUpdateDto.postContent() != null) {
                post.setPostContent(postContentUpdateDto.postContent());
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(postNotUpdatedError + " " + postUUID);
        }

        Post savedPost = postRepository.save(post);
        return mapPostToDto(savedPost);
    }

    public PostResponseDto likePost(String postUUID, PostLikeDto postLikeDto) {
        Post post = postRepository.findByPostUUID(postUUID)
                .orElseThrow(() -> new RuntimeException(postNotFoundError + " " + postUUID));

        if (!post.getAuthorUUID().equals(postLikeDto.userUUID())) {
            throw new RuntimeException(unauthorizedAccessError);
        }

        try {
            if (!post.getUuidsOfUsersWhoLikedThisPost().contains(postLikeDto.userUUID())) {
                post.getUuidsOfUsersWhoLikedThisPost().add(postLikeDto.userUUID());
                post.setLikeCount(post.getLikeCount() + 1);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(postNotFoundError + " " + postUUID);
        }

        Post updatedPost = postRepository.save(post);
        return mapPostToDto(updatedPost);
    }

    private PostResponseDto mapPostToDto(Post post) {
        return new PostResponseDto(
                post.getPostUUID(),
                post.getAuthorUUID(),
                post.getUuidsOfUsersWhoLikedThisPost(),
                post.getPostContent(),
                post.getDatePosted(),
                post.getShareCount(),
                post.getLikeCount(),
                post.getTags());
    }
}
