package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.User;

import java.util.List;

public interface UserService {
    User create (User user, boolean sender_admin);
    User update (User user);
    User findById (Long id);
    List<User> findAll();
    User findByEmail(String email);
    String encryptPassword(String password);
    User getLoggedUser();
    void userChangesPassword(String email, String oldPassword, String newPassword);
}
