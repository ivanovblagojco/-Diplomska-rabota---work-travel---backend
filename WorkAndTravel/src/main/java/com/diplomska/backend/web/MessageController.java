package com.diplomska.backend.web;

import com.diplomska.backend.helpers.MessageHelper;
import com.diplomska.backend.model.Message;
import com.diplomska.backend.service.interfaces.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @PostMapping("/createMessage")
    public Message createMessage(@RequestBody MessageHelper message){
            return this.messageService.create(message);
    }
}
