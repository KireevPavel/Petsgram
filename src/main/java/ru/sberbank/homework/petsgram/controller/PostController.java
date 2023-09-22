package ru.sberbank.homework.petsgram.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.homework.petsgram.model.Post;
import ru.sberbank.homework.petsgram.service.post.PostService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts", produces = "application/json")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Post create(@Valid @RequestBody Post post) {
        log.info("Поступил запрос на добавление поста.");
        return postService.add(post);
    }

    @PutMapping
    public Post updatePost(@Valid @RequestBody Post post) {
        log.info("Поступил запрос на изменения поста.");
        return postService.update(post);
    }

    @GetMapping()
    public List<Post> getPosts() {
        log.info("Поступил запрос на получение списка всех постов.");
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable int id) {
        log.info("Получен GET-запрос на получение поста");
        return postService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable int id) {
        log.info("Получен запрос на удаление поста");
        return postService.removeById(id);
    }


}
