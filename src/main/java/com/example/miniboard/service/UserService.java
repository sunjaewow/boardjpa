package com.example.miniboard.service;

import com.example.miniboard.domain.Role;
import com.example.miniboard.domain.User;
import com.example.miniboard.repository.RoleRepository;
import com.example.miniboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void addUser(String name, String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }
        Role role = roleRepository.findByName("ROLE_USER").get();
        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword(password);
        user1.setRoles(Set.of(role));

        userRepository.save(user1);

    }

    @Transactional
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public List<String> getRoles(int userId) {
        Set<Role> roles = userRepository.findById(userId).orElseThrow().getRoles();
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }


}
