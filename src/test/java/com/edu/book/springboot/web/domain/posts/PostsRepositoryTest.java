package com.edu.book.springboot.web.domain.posts;

import com.edu.book.springboot.domain.posts.Posts;
import com.edu.book.springboot.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void save_test(){
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author("dnjstj0623@gmail.com")
                        .build());

        List<Posts> list = postsRepository.findAll();

        Posts posts = list.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void baseTimeEntity_save(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,8,10,0,0,0);

        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        //when
        List<Posts> all = postsRepository.findAll();

        //then
        System.out.println(">>>>>>>>>> createDate : "+all.get(0).getCreatedDate() + ", modifiedData : "+all.get(0).getModifiedDate());

        assertThat(all.get(0).getCreatedDate()).isAfter(now);
        assertThat(all.get(0).getModifiedDate()).isAfter(now);
    }
}
