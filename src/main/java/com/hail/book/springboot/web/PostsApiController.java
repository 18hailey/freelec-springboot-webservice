package com.hail.book.springboot.web;

import com.hail.book.springboot.service.posts.PostsService;
import com.hail.book.springboot.web.dto.PostsResponseDto;
import com.hail.book.springboot.web.dto.PostsSaveRequestDto;
import com.hail.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 게시글 저장
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    // 게시글 수정
    @PutMapping("/api/v1/posts/{id}") //
    public Long update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    // 게시글 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id") Long id) {
        return postsService.findById(id);
    }
}
