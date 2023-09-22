package ru.sberbank.homework.petsgram.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.petsgram.exeption.NotFoundException;
import ru.sberbank.homework.petsgram.model.User;
import ru.sberbank.homework.petsgram.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStorage.getUsers().values());
    }

    @Override
    public User add(User user) {
        log.info("Поступил запрос на добавление пользователя. Пользователь добавлен.");
        userStorage.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (userStorage.getUsers().get(user.getId()) != null) {
            log.info("Поступил запрос на изменения пользователя. Пользователь изменён.");
            userStorage.update(user);
        } else {
            log.error("Поступил запрос на изменения пользователя. Пользователь не найден.");
            throw new NotFoundException("User not found.");
        }
        return user;
    }

    @Override
    public User getById(int id) {
        if (userStorage.getUsers().containsKey(id)) {
            log.info("Поступил запрос на получение пользователя.");
        } else {
            log.error("Поступил запрос на получение пользователя. Пользователь не найден.");
            throw new NotFoundException("Post not found.");
        }
        return userStorage.getById(id);
    }

    @Override
    public User removeById(int id) {
        if (userStorage.getUsers().containsKey(id)) {
            log.info("Поступил запрос на удаление пользователя.");
        } else {
            log.error("Поступил запрос на удаление пользователя. Пользователь не найден.");
            throw new NotFoundException("Post not found.");
        }
        return userStorage.removeById(id);
    }

}
