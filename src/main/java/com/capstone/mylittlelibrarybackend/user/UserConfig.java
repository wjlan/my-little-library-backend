package com.capstone.mylittlelibrarybackend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
//            User wanjun = new User(
//                    "wj",
//                    "Lan",
//                    "123456@gmail.com",
//                    "123456"
//            );
//            User alex = new User(
//                    "alex",
//                    "Wong",
//                    "654321@gmail.com",
//                    "654321"
//            );
//
//            repository.saveAll(
//                    List.of(wanjun, alex)
//            );
        };
    }
}
