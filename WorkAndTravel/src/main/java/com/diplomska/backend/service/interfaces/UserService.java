package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.User;

import java.util.List;

public interface UserService {
    User create (User user);
    User update (User user);
    User findById (Integer id);
    List<User> findAll();
}
