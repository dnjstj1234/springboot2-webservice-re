package com.edu.book.springboot.service.posts;

import com.edu.book.springboot.domain.posts.Posts;
import com.edu.book.springboot.domain.posts.PostsRepository;
import com.edu.book.springboot.web.dto.PostsResponseDto;
import com.edu.book.springboot.web.dto.PostsSaveRequestDto;
import com.edu.book.springboot.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateDto updateDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 업습니다. id="+id));

        posts.update(updateDto.getTitle(), updateDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

}
