package ru.sberbank.homework.petsgram.service.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.petsgram.exeption.NotFoundException;
import ru.sberbank.homework.petsgram.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Slf4j
@Component
public class PostDbService implements PostService {

    private JdbcTemplate jdbcTemplate;

    public PostDbService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Post mapRowToPost(ResultSet resultSet, int rowNum) throws SQLException {
        return Post.builder()
                .id(resultSet.getInt("post_id"))
                .author(resultSet.getString("author"))
                .creationDate(resultSet.getDate("creation_date").toLocalDate())
                .description(resultSet.getString("description"))
                .photoUrl(resultSet.getString("photo_url"))
                .build();
    }

    private Map<String, Object> toMap(Post post) {
        Map<String, Object> values = new HashMap<>();
        values.put("author", post.getAuthor());
        values.put("creation_date", post.getCreationDate());
        values.put("description", post.getDescription());
        values.put("photo_url", post.getPhotoUrl());
        return values;
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        SqlRowSet postRows = jdbcTemplate.queryForRowSet("SELECT * FROM posts");
        while (postRows.next()) {
            Post post = Post.builder()
                    .id(postRows.getInt("post_id"))
                    .author(postRows.getString("author"))
                    .creationDate(Objects.requireNonNull(postRows.getDate("creation_date")).toLocalDate())
                    .description(postRows.getString("description"))
                    .photoUrl(postRows.getString("photo_url"))
                    .build();
            posts.add(post);
        }
        return posts;
    }

    @Override
    public Post add(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("posts")
                .usingGeneratedKeyColumns("post_id");
        post.setId(simpleJdbcInsert.executeAndReturnKey(toMap(post)).intValue());
        log.info("Поступил запрос на добавление Поста. Пост добавлен.");
        return post;
    }

    @Override
    public Post update(Post post) {
        String sqlQuery = "UPDATE posts SET " +
                "author=?, creation_date=?, description=?, photo_url=? WHERE post_id=?";
        int rowsCount = jdbcTemplate.update(sqlQuery, post.getAuthor(), post.getCreationDate(), post.getDescription(),
                post.getPhotoUrl(), post.getId());

        if (rowsCount > 0) {
            return post;
        }
        throw new NotFoundException("Пост не найден.");
    }

    @Override
    public Post getById(int id) {
        String sqlQuery = "SELECT * FROM posts WHERE post_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPost, id);
        } catch (RuntimeException e) {
            throw new NotFoundException("Пост не найден.");
        }
    }

    @Override
    public Post removeById(int id) {
        Post post = getById(id);
        String sqlQuery = "DELETE FROM posts WHERE post_id = ?";
        jdbcTemplate.update(sqlQuery, id);
        return post;
    }

}
