package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.MessageHelper;
import com.diplomska.backend.model.Message;
import com.diplomska.backend.repository.MessageRepository;
import com.diplomska.backend.service.interfaces.MessageService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public Message create(MessageHelper message) {
        Message m = new Message();
        m.setTitle(message.getTitle());
        m.setDescription(message.getDescription());
        m.setSender(userService.findByEmail(message.getSender_email()));
        m.setReceiver(userService.findByEmail(message.getReceiver_email()));
        return messageRepository.save(m);
    }
}
