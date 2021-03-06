package com.project.study.web;

import com.project.study.domain.posts.Posts;
import com.project.study.domain.posts.PostsRepository;
import com.project.study.web.dto.PostsSaveRequestDTO;
import com.project.study.web.dto.PostsUpdateRequestDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록한다() throws Exception {
        String title = "title";
        String content = "content";

        PostsSaveRequestDTO postsSaveRequestDTO = PostsSaveRequestDTO.builder().title(title).content(content).author("나야나").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, postsSaveRequestDTO, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정한다() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDTO postsUpdateRequestDTO = PostsUpdateRequestDTO.builder().content(expectedContent).title(expectedTitle).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDTO> postsUpdateRequestDTOHttpEntity = new HttpEntity<>(postsUpdateRequestDTO);

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, postsUpdateRequestDTOHttpEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

}