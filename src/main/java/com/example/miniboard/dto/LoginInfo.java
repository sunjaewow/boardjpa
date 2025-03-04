package com.example.miniboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {
    private int userId;
    private String email;
    private String name;

    public LoginInfo(int userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
