package com.capstone.mylittlelibrarybackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public void registerUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @PutMapping(path = "/{userId}")
    public void updateBook(@PathVariable("userId") Long bookId, @RequestBody User user) {
        userService.updateUser(bookId, user);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
