package ru.sberbank.homework.petsgram.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.petsgram.exeption.NotFoundException;
import ru.sberbank.homework.petsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Slf4j
@Component
public class UserDbService implements UserService {

    private JdbcTemplate jdbcTemplate;

    public UserDbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM users");
        while (rowSet.next()) {
            User user = User.builder()
                    .id(rowSet.getInt("user_id"))
                    .email(rowSet.getString("email"))
                    .login(rowSet.getString("login"))
                    .name(rowSet.getString("name"))
                    .birthday(Objects.requireNonNull(rowSet.getDate("birthday")).toLocalDate())
                    .build();
            users.add(user);
        }
        return users;
    }

    @Override
    public User add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        log.info("Поступил запрос на добавление пользователя. Пользователь добавлен.");
        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET " +
                "email=?, login=?, name=?, birthday=? WHERE user_id=?";
        int rowsCount = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (rowsCount > 0) {
            log.info("Поступил запрос на изменения пользователя. Пользователь изменён.");
            return user;
        }
        throw new NotFoundException("Пользователь не найден.");
    }

    @Override
    public User removeById(int id) {
        User user = getById(id);
        String sqlQuery = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
        return user;
    }

    @Override
    public User getById(int id) {
        String sqlQuery = "SELECT * FROM users WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (RuntimeException e) {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

}
