package com.diplomska.backend.web;

import com.diplomska.backend.exceptions.TokenHasExpireException;
import com.diplomska.backend.exceptions.UserAlreadyExistsException;
import com.diplomska.backend.helpers.UserHelper;
import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.TokenService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;


@RestController
@RequestMapping("/rest")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @PutMapping("/userUpdateUser")
    public User userUpdateUser(@RequestBody User user){
        var userDb = userService.findByEmail(user.getEmail());
        userDb.setName(user.getName());
        userDb.setSurname(user.getSurname());

        return this.userService.update(userDb);
    }
    @GetMapping("/confirm-account")
    public void confirmAccount(@RequestParam String token, HttpServletResponse response) throws IOException {
        Token tokenObj  = tokenService.findByToken(token);

        if(tokenObj!=null){
            if(tokenObj.getDate_expiration().isBefore(OffsetDateTime.now())){
                tokenService.deleteByToken(token);
                response.sendRedirect("http://localhost:3000/first_register?m=expire");
            }else{
                User user = tokenObj.getUser();
                if(user.getIs_enabled()){
                    response.sendRedirect("http://localhost:3000/first_register?m=exists");
                }else{
                    user.setIs_enabled(true);
                    userService.update(user);
                }
                tokenService.deleteByToken(token);

                response.sendRedirect("http://localhost:3000/first_register?m=success");
            }
        }
        response.sendRedirect("http://localhost:3000/first_register?m=exists");

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @GetMapping("/getLoggedUser")
    public UserHelper getLoggedUser(){
        return userService.getLoggedUser().getAsUserHelper();
    }
}
