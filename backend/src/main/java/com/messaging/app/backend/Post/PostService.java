package com.messaging.app.backend.Post;

import com.messaging.app.backend.Exceptions.PostNotCreatedException;
import com.messaging.app.backend.Exceptions.PostNotFoundException;
import com.messaging.app.backend.Pagination.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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
        Page<Post> page = postRepository.findAllPaginatedPosts(pageRequest);

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
