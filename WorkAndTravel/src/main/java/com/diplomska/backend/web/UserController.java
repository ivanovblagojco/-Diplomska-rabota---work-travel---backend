package com.diplomska.backend.web;

import com.diplomska.backend.exceptions.TokenHasExpireException;
import com.diplomska.backend.exceptions.UserAlreadyExistsException;
import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.TokenService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/confirm-account")
    public void confirmAccount(@RequestParam String token){
        Token tokenObj  = tokenService.findByToken(token);

        if(tokenObj!=null){
            if(tokenObj.getDate_expiration().isBefore(OffsetDateTime.now())){
                tokenService.deleteByToken(token);
                throw new TokenHasExpireException();
            }else{
                User user = tokenObj.getUser();
                if(user.getIs_enabled()){
                    throw new UserAlreadyExistsException();
                }else{
                    user.setIs_enabled(true);
                    userService.update(user);
                }
                tokenService.deleteByToken(token);
            }
        }
    }
}
