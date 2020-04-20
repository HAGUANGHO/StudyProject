package com.project.study.web;

import com.project.study.service.posts.PostsService;
import com.project.study.web.dto.PostsResponseDTO;
import com.project.study.web.dto.PostsSaveRequestDTO;
import com.project.study.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO postsSaveRequestDTO) {
        return postsService.save(postsSaveRequestDTO);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO postsUpdateRequestDTO) {
        return postsService.update(id, postsUpdateRequestDTO);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}