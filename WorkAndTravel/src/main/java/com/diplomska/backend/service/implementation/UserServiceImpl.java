package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.UserNotFoundException;
import com.diplomska.backend.model.User;
import com.diplomska.backend.repository.UserRepository;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return this.userRepository.save(user);
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
}
