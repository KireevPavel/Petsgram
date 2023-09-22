package ru.sberbank.homework.petsgram.controller;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.homework.petsgram.model.User;
import ru.sberbank.homework.petsgram.service.user.UserService;
import ru.sberbank.homework.petsgram.service.user.UserServiceImpl;
import ru.sberbank.homework.petsgram.storage.user.InMemoryUserStorage;
import ru.sberbank.homework.petsgram.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    UserController controller;
    UserService userService;
    UserStorage userStorage;
    User testUser;

    @BeforeEach
    protected void init() {
        userStorage = new InMemoryUserStorage();
        userService = new UserServiceImpl(userStorage);
        controller = new UserController(userService);

        testUser = User.builder()
                .name("John")
                .email("john@mail.ru")
                .login("login")
                .birthday(LocalDate.of(1987, 4, 14))
                .build();
    }

    @Test
    public void createUser_NameIsBlank_NameIsLoginTest() {
        testUser.setName("");
        try {
            controller.create(testUser);
        } catch (ValidationException e) {
            Assertions.assertEquals("Некорректно указано имя пользователя.", e.getMessage());
        }
    }

    @Test
    void createUser_BirthdayInFuture_badRequestTest() {
        testUser.setBirthday(LocalDate.parse("2024-10-12"));
        try {
            controller.create(testUser);
        } catch (ValidationException e) {
            assertEquals("Неверно указана дата рождения", e.getMessage());
        }
    }

}