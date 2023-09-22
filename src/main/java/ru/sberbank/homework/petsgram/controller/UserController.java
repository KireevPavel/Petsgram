package ru.sberbank.homework.petsgram.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.homework.petsgram.model.User;
import ru.sberbank.homework.petsgram.service.user.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Поступил запрос на создание пользователя.");
        return userService.add(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Поступил запрос на обновление пользователя.");
        return userService.update(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Поступил запрос на получение списка пользователей.");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Поступил запрос на получение пользователя по id.");
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public User removeUserById(@PathVariable int id) {
        log.info("Поступил запрос на удаление пользователя по id.");
        return userService.removeById(id);
    }

}


