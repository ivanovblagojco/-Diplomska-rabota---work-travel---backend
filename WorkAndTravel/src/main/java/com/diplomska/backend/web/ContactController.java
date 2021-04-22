package com.diplomska.backend.web;

import com.diplomska.backend.model.Contact;
import com.diplomska.backend.service.interfaces.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @GetMapping("/getAllContacts/{page}/{size}")
    public Page<Contact> getAllContacts(@PathVariable int page, @PathVariable int size){
        List<Contact> list = this.contactService.getAllContacts();

        list = list.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), list.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startIdx, endIdx),pageable,list.size());
    }
}
