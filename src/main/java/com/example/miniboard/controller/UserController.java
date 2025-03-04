package com.example.miniboard.controller;

import com.example.miniboard.dto.LoginInfo;
import com.example.miniboard.dto.User;
import com.example.miniboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }

    @PostMapping("/userReg")
    public String userReg(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        userService.addUser(name, email, password);

        return "redirect:/";
    }

    @GetMapping("/loginform")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession httpSession) {
        User user = userService.getUser(email);

        try {
            if (user.getPassword().equals(password)) {
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), email, user.getName());
                httpSession.setAttribute("loginInfo", loginInfo);
            } else {
                throw new RuntimeException("정보가 일치하지않음.");
            }
        } catch (Exception exception) {
            return "redirect:/loginform?error=true";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("loginInfo");
        return "redirect:/";
    }
}
