package ru.sberbank.homework.petsgram.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.sberbank.homework.petsgram.model.Post;
import ru.sberbank.homework.petsgram.service.post.PostDbService;
import ru.sberbank.homework.petsgram.service.post.PostService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(PostDbService.class)
class PostDbStorageTest {

    @Autowired
    private PostService postDbService;
    Post post;

    @BeforeEach
    void setUp() {

        post = Post.builder()
                .author("name")
                .creationDate(LocalDate.of(1999, 8, 17))
                .description("desc")
                .photoUrl("https://w.forfun.com/fetch/96/96e20d242c141a43cfb6295be5aa3e3e.jpeg")
                .build();

    }

    @Test
    void addFilmTest() {
        postDbService.add(post);
        assertEquals(post, postDbService.getById(post.getId()));
    }

    @Test
    void updateFilmTest() {
        postDbService.add(post);
        assertEquals(post, postDbService.getById(post.getId()));

        post.setAuthor("updateAuthor");
        postDbService.update(post);
        assertEquals("updateAuthor", postDbService.getById(post.getId()).getAuthor());
    }

}
