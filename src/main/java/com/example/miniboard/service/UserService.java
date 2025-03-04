package com.example.miniboard.service;

import com.example.miniboard.dao.UserDao;
import com.example.miniboard.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public void addUser(String name, String email, String password) {
        userDao.addUser(name, email, password);
    }

    public User getUser(String email) {

        return userDao.getUser(email);
    }
}
