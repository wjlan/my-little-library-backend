package com.capstone.mylittlelibrarybackend;

import com.capstone.mylittlelibrarybackend.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
public class MyLittleLibraryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyLittleLibraryBackendApplication.class, args);
    }

    @GetMapping
    public List<User> hello() {
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
