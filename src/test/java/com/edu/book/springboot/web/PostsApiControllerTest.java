package com.edu.book.springboot.web;

import com.edu.book.springboot.domain.posts.Posts;
import com.edu.book.springboot.domain.posts.PostsRepository;
import com.edu.book.springboot.web.dto.PostsSaveRequestDto;
import com.edu.book.springboot.web.dto.PostsUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void posts_save(){
        String title = "title";
        String content = "content";

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, dto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> list = postsRepository.findAll();

        Posts posts = list.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void posts_update() throws Exception {
        Posts savePosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        Long updateId = savePosts.getId();

        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateDto dto = PostsUpdateDto.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateDto> requestEntity = new HttpEntity<>(dto);

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void posts_findById(){  //????
        Posts savePosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        Long id = savePosts.getId();

        String url = "http://localhost:"+port+"/api/v1/posts/"+id;

        HttpEntity<Long> requestEntity = new HttpEntity<>(id);

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("title");
        assertThat(all.get(0).getContent()).isEqualTo("content");
    }
}
