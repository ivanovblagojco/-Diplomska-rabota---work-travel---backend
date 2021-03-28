package com.diplomska.backend.web;

import com.diplomska.backend.helpers.ConversationHelper;
import com.diplomska.backend.model.Conversation;
import com.diplomska.backend.model.Message;
import com.diplomska.backend.model.User;
import com.diplomska.backend.model.UserConversation;
import com.diplomska.backend.service.interfaces.ConversationService;
import com.diplomska.backend.service.interfaces.UserConversationService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class ConversationController {
    private final UserConversationService userConversationService;
    private final UserService userService;
    private final ConversationService conversationService;

    public ConversationController(UserConversationService userConversationService, UserService userService, ConversationService conversationService) {
        this.userConversationService = userConversationService;
        this.userService = userService;
        this.conversationService = conversationService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @GetMapping("/getConversationsForLoggedUser")
    public List<ConversationHelper> getConversationsForLoggedUser(){
        List<ConversationHelper> conversationHelperList = userConversationService.findAll().stream().filter(uc->uc.getUser().equals(userService.getLoggedUser())).map(UserConversation::getConversation).map(Conversation::getAsConversationHelper).collect(Collectors.toList());

        User user = userService.getLoggedUser();

        for (ConversationHelper ch:conversationHelperList) {
            if(ch.getName().split("_")[0].equals(user.getEmail())){
                ch.setOther_person(ch.getName().split("_")[1]);
            }else{
                ch.setOther_person(ch.getName().split("_")[0]);
            }
        }

        return conversationHelperList;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @GetMapping("/getConversationMessages/{name}")
    public List<Message> getConversationMessages(@PathVariable String name){
        List<Message> messages = this.conversationService.findByName(name).getMessages();
        Collections.sort(messages, Collections.reverseOrder());
        return messages;
    }

}
