package ru.sberbank.homework.petsgram.storage.user;

import ru.sberbank.homework.petsgram.model.User;

import java.util.HashMap;

public interface UserStorage {

    HashMap<Integer, User> getUsers();

    User add(User user);

    User update(User user);

    User getById(int id);

    User removeById(int id);
}
