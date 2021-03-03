package com.diplomska.backend.web;

import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@PreAuthorize("isAuthenticated()")
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
    public User adminCreateUser(@RequestHeader("email") String email, @RequestHeader("password") String password,@RequestBody User user){
        //this api is for agencies creation - the most
        user.setPassword(password);
        user.setEmail(email);
        return this.userService.create(user, true);
    }
    @PostMapping("/userCreateUser")
    public User userCreateUser(@RequestHeader("email") String email, @RequestHeader("password") String password,@RequestBody User user){
        //this api is only for person users creation - the most
        user.setPassword(password);
        user.setEmail(email);
        return this.userService.create(user, false);
    }
}
