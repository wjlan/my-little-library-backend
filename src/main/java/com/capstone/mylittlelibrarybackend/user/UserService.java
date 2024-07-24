package com.capstone.mylittlelibrarybackend.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<User> getUsers() {
        return List.of (
                new User(
                        1L,
                        "wj",
                        "Lan",
                        "123456@gmail.com",
                        "123456"
                )
        );
    }
}
