package com.diplomska.backend.web;

import com.diplomska.backend.exceptions.TokenHasExpireException;
import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.TokenService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/rest")
public class ForgotPasswordController {
    private final JavaMailSender javaMailSender;

    private final UserService userService;
    private final TokenService tokenService;

    public ForgotPasswordController(JavaMailSender javaMailSender, UserService userService, TokenService tokenService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.tokenService = tokenService;
    }



    @PostMapping("/forgot_password")
    public void processForgotPassword(@RequestHeader String email) {
        String token = UUID.randomUUID().toString();
        User user = userService.findByEmail(email);

        if(user!=null){
            try {

                Token tokenObj = new Token();
                tokenObj.setToken(token);
                tokenObj.setUser(user);
                tokenObj.setDate_expiration(OffsetDateTime.now().plusMinutes(30));
                tokenService.create(tokenObj);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(email);
                mailMessage.setSubject("Change password!");
                mailMessage.setFrom("inteligenta05@gmail.com");
                mailMessage.setText("Click here to set your new password : "
                        +"http://localhost:3000/reset_password?token="+token);

                javaMailSender.send(mailMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    @PostMapping("/reset_password")
    public void processResetPassword(@RequestHeader String token, @RequestHeader String password) {

        Token tokenObj = tokenService.findByToken(token);
        User user = tokenObj.getUser();

        if(user!=null){
            if(tokenObj.getDate_expiration().isBefore(OffsetDateTime.now())){
                tokenService.deleteByToken(token);
                throw new TokenHasExpireException();
            }else{
                user.setPassword(userService.encryptPassword(password));
                user.setIs_enabled(true);
                userService.update(user);
                tokenService.deleteByToken(token);
            }
        }
    }
}
