package org.macnigor.serverhttps.repository;

import org.macnigor.serverhttps.model.User;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@RegisterReflectionForBinding(User.class)
@Repository
public class UserRepositorySql implements UserRepository, RowMapper<User> {
    private final JdbcOperations jdbcOperations;

    public UserRepositorySql(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        // stream().findFirst() — хороший способ для Optional
        return jdbcOperations.query("select * from users where username = ?", this, username)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> findByApiKey(String apiKey) {
        // Исправлено имя колонки на api_key (проверьте свою БД)
        return jdbcOperations.query("select * from users where apikey = ?", this, apiKey)
                .stream()
                .findFirst();
    }

    @Override
    public void save(User user) {
        // Использован метод update и корректный SQL INSERT
        jdbcOperations.update(
                "insert into users(username, password, apikey) values (?, ?, ?)",
                user.username(),
                user.password(),
                user.apikey()
        );
    }

    @Override
    public List<User> findAll() {
        return jdbcOperations.query("select * from users", this);
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Убедитесь, что имена в кавычках совпадают с именами в БД
        return new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("apikey")
        );
    }
}



