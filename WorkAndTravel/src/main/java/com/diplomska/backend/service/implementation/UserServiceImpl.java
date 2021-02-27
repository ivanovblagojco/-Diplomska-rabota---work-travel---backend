package com.diplomska.backend.service.implementation;

import com.diplomska.backend.constants.RoleContstants;
import com.diplomska.backend.exceptions.UserNotFoundException;
import com.diplomska.backend.model.User;
import com.diplomska.backend.repository.UserRepository;
import com.diplomska.backend.service.interfaces.RoleService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
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
            return userRepository.save(user);
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
}
