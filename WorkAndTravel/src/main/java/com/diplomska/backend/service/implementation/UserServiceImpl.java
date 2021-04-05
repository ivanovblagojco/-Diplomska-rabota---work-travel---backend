package com.diplomska.backend.service.implementation;

import com.diplomska.backend.constants.RoleContstants;
import com.diplomska.backend.exceptions.UserNotFoundException;
import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;
import com.diplomska.backend.repository.UserRepository;
import com.diplomska.backend.service.interfaces.RoleService;
import com.diplomska.backend.service.interfaces.TokenService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final TokenService tokenService;

    private  final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleService roleService, TokenService tokenService, JavaMailSender javaMailSender, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.javaMailSender = javaMailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User user, boolean sender_admin) {
        if(sender_admin){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(roleService.findByName(RoleContstants.ROLE_PREFIX+RoleContstants.ROLE_USER));
            user.setIs_enabled(false);
            user = userRepository.save(user);

            Token token = new Token();
            token.setToken(UUID.randomUUID().toString());
            token.setDate_expiration(OffsetDateTime.now().plusMinutes(3));
            token.setUser(user);
            tokenService.create(token);

            SendTokenForUser(user.getEmail(), token.getToken());
            return user;
        }
    }

    @Override
    public User update(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findAll().stream().filter(u->u.getEmail().equals(email)).collect(Collectors.toList()).get(0);
    }
    public void SendTokenForUser(String email, String token){
        try {


            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(email);
            mailMessage.setSubject("Change password!");
            mailMessage.setFrom("diplomskar@gmail.com");
            mailMessage.setText("Click here to set your new password : "
                  +"http://localhost:8080/rest/confirm-account?token="+token);

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String encryptPassword(String password){
        return this.bCryptPasswordEncoder.encode(password);
    }

    @Override
    public User getLoggedUser() {
        //get the email
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getPrincipal().toString();

        //find user from DB
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void userChangesPassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);

        String userPass = user.getPassword();
        if(bCryptPasswordEncoder.matches(oldPassword, userPass)){
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            this.update(user);
        }else{
            throw new RuntimeException();
        }
    }
}
