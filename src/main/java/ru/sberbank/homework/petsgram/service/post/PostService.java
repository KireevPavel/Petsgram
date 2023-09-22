package ru.sberbank.homework.petsgram.service.post;

import org.springframework.stereotype.Service;
import ru.sberbank.homework.petsgram.model.Post;

import java.util.List;

@Service

public interface PostService {

    List<Post> findAll();

    Post add(Post post);

    Post update(Post post);

    Post getById(int id);

    Post removeById(int id);

}

