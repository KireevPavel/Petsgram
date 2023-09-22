package ru.sberbank.homework.petsgram.storage.post;

import ru.sberbank.homework.petsgram.model.Post;

import java.util.HashMap;

public interface PostStorage {

    HashMap<Integer, Post> getPosts();

    Post add(Post post);

    Post update(Post post);

    Post getById(int id);

    Post removeById(int id);
}
