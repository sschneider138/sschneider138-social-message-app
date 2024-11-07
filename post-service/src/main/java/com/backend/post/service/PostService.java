package com.backend.post.service;

import com.backend.post.dto.PageDto;
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

    public PostResponseDto createPost(PostCreationDto postCreationDto) {
        Post post = Post.builder()
                .authorUUID(postCreationDto.authorUUID())
                .postContent(postCreationDto.postContent())
                .tags(postCreationDto.tags())
                .build();

        Post savedPost = postRepository.save(post);
        return mapPostToDto(savedPost);
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
                post.getTags()
        );
    }
}
