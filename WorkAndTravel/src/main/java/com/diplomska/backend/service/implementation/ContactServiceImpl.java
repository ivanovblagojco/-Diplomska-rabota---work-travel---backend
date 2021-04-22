package com.diplomska.backend.service.implementation;

import com.diplomska.backend.model.Contact;
import com.diplomska.backend.repository.ContactRepository;
import com.diplomska.backend.service.interfaces.ContactService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final UserService userService;

    public ContactServiceImpl(ContactRepository contactRepository, UserService userService) {
        this.contactRepository = contactRepository;
        this.userService = userService;
    }

    @Override
    public Contact create(Contact contact) {
        contact.setUser(userService.findByEmail("admin@admin.com"));
        return this.contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return this.contactRepository.findAll();
    }
}
