package ru.sberbank.homework.petsgram.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.petsgram.model.User;

import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    private int generateId() {
        return ++id;
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User add(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(int id) {
        return users.get(id);
    }

    @Override
    public User removeById(int id) {
        return users.remove(id);
    }
}

