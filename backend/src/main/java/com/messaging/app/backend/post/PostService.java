package com.messaging.app.backend.post;

import com.messaging.app.backend.exceptions.PostNotCreatedException;
import com.messaging.app.backend.exceptions.PostNotFoundException;
import com.messaging.app.backend.pagination.PageDto;
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
    
    public List<PostResponseDto> getAllPosts() throws PostNotFoundException {
        return postRepository.findAll().stream()
                .map(
                        post -> new PostResponseDto(
                                post.getAuthor(),
                                post.getLikedByUsers(),
                                post.getPostContent(),
                                post.getDatePosted(),
                                post.getShareCount(),
                                post.getTags()))
                .collect(Collectors.toList());

    }


    public PageDto<PostResponseDto> getAllPaginatedPosts(int pageIndex, int itemsPerPage) throws PostNotFoundException {
        PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
        Page<Post> page = postRepository.findAll(pageRequest);

        Page<PostResponseDto> dtoPage = page.map(
                post -> new PostResponseDto(
                        post.getAuthor(),
                        post.getLikedByUsers(),
                        post.getPostContent(),
                        post.getDatePosted(),
                        post.getShareCount(),
                        post.getTags()
                )
        );

        return new PageDto<>(
                dtoPage.getContent(),
                dtoPage.getTotalPages(),
                (int) dtoPage.getTotalElements(),
                dtoPage.getNumber(),
                dtoPage.getSize()
        );
    }

    public PostResponseDto createPost(PostCreationDto postCreationDto) throws PostNotCreatedException {
        Post post = Post.builder()
                .author(postCreationDto.author())
                .postContent(postCreationDto.postContent())
                .build();

        Post savedPost = postRepository.save(post);

        return new PostResponseDto(
                savedPost.getAuthor(),
                savedPost.getLikedByUsers(),
                savedPost.getPostContent(),
                savedPost.getDatePosted(),
                savedPost.getShareCount(),
                savedPost.getTags()
        );
    }
}
