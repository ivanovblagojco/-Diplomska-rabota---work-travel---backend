package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.RoleNotFoundException;
import com.diplomska.backend.model.Role;
import com.diplomska.backend.repository.RoleRepository;
import com.diplomska.backend.service.interfaces.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        return this.roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
