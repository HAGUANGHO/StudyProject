package com.project.study.service.posts;

import com.project.study.domain.posts.Posts;
import com.project.study.domain.posts.PostsRepository;
import com.project.study.web.dto.PostsResponseDTO;
import com.project.study.web.dto.PostsSaveRequestDTO;
import com.project.study.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDTO postsSaveRequestDTO) {
        return postsRepository.save(postsSaveRequestDTO.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDTO postsUpdateRequestDTO) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(postsUpdateRequestDTO.getTitle(), postsUpdateRequestDTO.getContent());
        return id;
    }

    public PostsResponseDTO findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDTO(posts);
    }

}
