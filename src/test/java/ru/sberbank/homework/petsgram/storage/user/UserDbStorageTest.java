package ru.sberbank.homework.petsgram.storage.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.sberbank.homework.petsgram.model.User;
import ru.sberbank.homework.petsgram.service.user.UserDbService;
import ru.sberbank.homework.petsgram.service.user.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(UserDbService.class)
class UserDbStorageTest {

    @Autowired
    private UserService userDbService;
    User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .email("mail@mail.mail")
                .login("login")
                .name("login")
                .birthday(LocalDate.of(1999, 8, 17))
                .build();
    }

    @Test
    void shouldCreateAndUpdateAndGetUser() {
        userDbService.add(user);
        assertEquals(user, userDbService.getById(user.getId()));
        assertEquals(user.getLogin(), userDbService.getById(user.getId()).getName());
        user.setEmail("lol@lol.lol");
        userDbService.update(user);
        assertEquals(user, userDbService.getById(user.getId()));
        assertEquals(1, userDbService.findAll().size());
        assertEquals(user, userDbService.getById(user.getId()));
    }

}