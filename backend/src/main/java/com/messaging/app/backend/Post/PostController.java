package com.messaging.app.backend.Post;

import com.messaging.app.backend.Pagination.PageDto;
import com.messaging.app.backend.Pagination.PaginationRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/all/paginated")
    public ResponseEntity<PageDto<PostResponseDto>> getAllPaginatedPosts(
            @RequestBody PaginationRequestDto paginationRequestDto) {
        int currentPage = Math.max(paginationRequestDto.pageIndex(), 0);
        int currentLimit = (paginationRequestDto.itemsPerPage() > 0) ? paginationRequestDto.itemsPerPage() : 10;
        PageDto<PostResponseDto> pageDto = postService.getAllPaginatedPosts(currentPage, currentLimit);
        return ResponseEntity.ok(pageDto);
    }

    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> newPost(@Valid @RequestBody PostCreationDto postCreationDto) {
        PostResponseDto savedPost = postService.createPost(postCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }


}
