package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.Contact;

import java.util.List;

public interface ContactService {
    Contact create(Contact contact);
    List<Contact> getAllContacts();
}
