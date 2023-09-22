package ru.sberbank.homework.petsgram.service.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.petsgram.exeption.NotFoundException;
import ru.sberbank.homework.petsgram.model.Post;
import ru.sberbank.homework.petsgram.storage.post.PostStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class PostServiceImpl implements PostService {
    public PostServiceImpl(PostStorage postStorage) {
        this.postStorage = postStorage;
    }

    private PostStorage postStorage;

    @Override
    public List<Post> findAll() {
        log.info("Поступил запрос на получение списка всех постов.");
        return new ArrayList<>(postStorage.getPosts().values());
    }

    @Override
    public Post add(Post post) {
        log.info("Получен запрос на создание поста. Пост создан.");
        return postStorage.add(post);
    }

    @Override
    public Post update(Post post) {
        if (postStorage.getPosts().get(post.getId()) != null) {
            log.info("Получен запрос на обновление поста. Пост обновлен.");
            postStorage.add(post);
        } else {
            log.error("Поступил запрос на обновление поста. Пост не найден.");
            throw new NotFoundException("Post not found.");
        }
        return post;
    }

    @Override
    public Post getById(int id) {
        if (postStorage.getPosts().containsKey(id)) {
            log.info("Поступил запрос на получение поста.");
        } else {
            log.error("Поступил запрос на получение поста. Пост не найден.");
            throw new NotFoundException("Post not found.");
        }
        return postStorage.getById(id);
    }

    @Override
    public Post removeById(int id) {
        if (postStorage.getPosts().containsKey(id)) {
            log.info("Поступил запрос на удаление поста по id.");
        } else {
            log.error("Поступил запрос на удаление поста. Пост не найден.");
            throw new NotFoundException("Post not found.");
        }
        return postStorage.removeById(id);
    }

}
