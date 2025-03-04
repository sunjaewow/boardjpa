package com.example.miniboard.dao;

import com.example.miniboard.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class UserDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    public void addUser(String name, String email, String password) {
        String sql = "insert into user (name, email, password) values (:name, :email, :password)";
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        jdbcTemplate.update(sql, params);

        String getUserIdSql = "select last_insert_id()";
        int userId = jdbcTemplate.queryForObject(getUserIdSql, Map.of(), Integer.class);
        user.setUserId(userId);
    }

    public User getUser(String email) {
        String sql = "select email,password from user where email=:email";
        RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        User user = jdbcTemplate.queryForObject(sql, Map.of("email", email), rowMapper);
        return user;
    }

}
