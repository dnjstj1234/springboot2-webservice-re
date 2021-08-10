package com.edu.book.springboot.web;

import com.edu.book.springboot.service.posts.PostsService;
import com.edu.book.springboot.web.dto.PostsResponseDto;
import com.edu.book.springboot.web.dto.PostsSaveRequestDto;
import com.edu.book.springboot.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto updateDto){
        return postsService.update(id, updateDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
