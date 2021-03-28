package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.MessageHelper;
import com.diplomska.backend.model.Conversation;
import com.diplomska.backend.model.Message;
import com.diplomska.backend.model.UserConversation;
import com.diplomska.backend.repository.MessageRepository;
import com.diplomska.backend.service.interfaces.ConversationService;
import com.diplomska.backend.service.interfaces.MessageService;
import com.diplomska.backend.service.interfaces.UserConversationService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ConversationService conversationService;
    private final UserConversationService userConversationService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ConversationService conversationService, UserConversationService userConversationService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.conversationService = conversationService;
        this.userConversationService = userConversationService;
    }

    @Override
    public Message create(MessageHelper message) {
        Conversation conversation =null;
        if(conversationService.findByName(message.getSender_email()+"_"+message.getReceiver_email())!=null)
        {
            conversation=conversationService.findByName(message.getSender_email()+"_"+message.getReceiver_email());
        }else if(conversationService.findByName(message.getReceiver_email()+"_"+message.getSender_email())!=null){
            conversation=  conversationService.findByName(message.getReceiver_email()+"_"+message.getSender_email());
        }else{
            conversation= new Conversation();
            conversation.setName(message.getSender_email()+"_"+message.getReceiver_email());
            conversation=conversationService.create(conversation);

            UserConversation userConversationSender = new UserConversation();
            userConversationSender.setUser(userService.findByEmail(message.getSender_email()));
            userConversationSender.setConversation(conversation);
            userConversationService.create(userConversationSender);

            UserConversation userConversationReceiver = new UserConversation();
            userConversationReceiver.setUser(userService.findByEmail(message.getReceiver_email()));
            userConversationReceiver.setConversation(conversation);
            userConversationService.create(userConversationReceiver);
        }


        Message m = new Message();
        m.setTitle(message.getTitle());
        m.setDescription(message.getDescription());
        m.setSender(userService.findByEmail(message.getSender_email()));
        m.setReceiver(userService.findByEmail(message.getReceiver_email()));
        m.setConversation(conversation);
        return messageRepository.save(m);
    }
}
