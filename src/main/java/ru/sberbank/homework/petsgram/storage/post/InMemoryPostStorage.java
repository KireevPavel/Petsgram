package ru.sberbank.homework.petsgram.storage.post;

import org.springframework.stereotype.Component;
import ru.sberbank.homework.petsgram.model.Post;

import java.util.HashMap;

@Component
public class InMemoryPostStorage implements PostStorage {

    private HashMap<Integer, Post> posts = new HashMap<>();
    private int id = 0;

    private int generateId() {
        return ++id;
    }

    @Override
    public HashMap<Integer, Post> getPosts() {
        return posts;
    }

    @Override
    public Post add(Post post) {
        post.setId(generateId());
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public Post update(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public Post getById(int id) {
        return posts.get(id);
    }

    @Override
    public Post removeById(int id) {
        return posts.remove(id);
    }
}
