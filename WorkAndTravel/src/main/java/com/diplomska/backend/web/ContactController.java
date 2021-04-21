package com.diplomska.backend.web;

import com.diplomska.backend.model.Contact;
import com.diplomska.backend.service.interfaces.ContactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/createContact")
    public Contact createContact(@RequestBody Contact contact){
        return this.contactService.create(contact);
    }
}
