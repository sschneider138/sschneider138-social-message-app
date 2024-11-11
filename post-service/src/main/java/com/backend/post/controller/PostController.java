package com.backend.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.post.dto.PageDto;
import com.backend.post.dto.PaginationRequestDto;
import com.backend.post.dto.PostContentUpdateDto;
import com.backend.post.dto.PostCreationDto;
import com.backend.post.dto.PostResponseDto;
import com.backend.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    public PostResponseDto createPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        return postService.createPost(postCreationDto);
    }

    @PatchMapping("/update/{postUUID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PostResponseDto updatePostContent(@RequestParam String postUUID,
            @RequestBody PostContentUpdateDto postContentUpdateDto) {
        return postService.updatePostContent(postUUID, postContentUpdateDto);
    }
}
