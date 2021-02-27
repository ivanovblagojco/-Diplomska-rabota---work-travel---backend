package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.Role;
import java.util.List;

public interface RoleService {
    Role create (Role role);
    Role update (Role role);
    Role findById (Long id);
    List<Role> findAll();
    Role findByName(String name);
}
