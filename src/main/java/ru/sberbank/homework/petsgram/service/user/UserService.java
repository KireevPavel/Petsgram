package ru.sberbank.homework.petsgram.service.user;

import org.springframework.stereotype.Service;
import ru.sberbank.homework.petsgram.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> findAll();

    User add(User user);

    User update(User user);

    User getById(int id);

    User removeById(int id);

}
