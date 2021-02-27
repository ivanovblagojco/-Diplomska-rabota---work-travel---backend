package com.diplomska.backend.web;

import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return this.userService.findAll();
    }

    @PostMapping("/adminCreateUser")
    public User adminCreateUser(@RequestHeader("email") String email, @RequestHeader String password,@RequestBody User user){
        user.setPassword(password);
        user.setEmail(email);
        return this.userService.create(user, true);
    }
}
