package ru.sberbank.homework.petsgram.controller;


import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.homework.petsgram.model.Post;
import ru.sberbank.homework.petsgram.service.post.PostService;
import ru.sberbank.homework.petsgram.service.post.PostServiceImpl;
import ru.sberbank.homework.petsgram.storage.post.InMemoryPostStorage;
import ru.sberbank.homework.petsgram.storage.post.PostStorage;

import java.time.LocalDate;

public class PostControllerTest {

    PostController controller;
    PostService postService;
    PostStorage postStorage;
    Post testPost;

    @BeforeEach
    protected void init() {
        postStorage = new InMemoryPostStorage();
        postService = new PostServiceImpl(postStorage);
        controller = new PostController(postService);
        testPost = Post.builder()
                .author("John")
                .creationDate(LocalDate.of(1987, 4, 14))
                .description("тестовое описание")
                .photoUrl("https://w.forfun.com/fetch/96/96e20d242c141a43cfb6295be5aa3e3e.jpeg")
                .build();
    }

    @Test
    void createNewCorrectPost_isOkTest() {
        controller.create(testPost);
        Assertions.assertEquals(testPost, postService.getById(1));
    }

    @Test
    void createPost_NameIsBlank_badRequestTest() {
        testPost.setAuthor("");
        try {
            controller.create(testPost);
        } catch (ValidationException e) {
            Assertions.assertEquals("Некорректно указано название фильма.", e.getMessage());
        }
    }


    @Test
    void createPost_IncorrectDescription_badRequestTest() {
        testPost.setDescription("Размер описания значительно превышает двести символов, а может и не превышает " +
                "(надо посчитать). Нет, к сожалению размер описания фильма сейчас не превышает двести символов," +
                "но вот сейчас однозначно стал превышать двести символов!");
        try {
            controller.create(testPost);
        } catch (ValidationException e) {
            Assertions.assertEquals("Превышено количество символов в описании фильма.", e.getMessage());
        }
    }


}