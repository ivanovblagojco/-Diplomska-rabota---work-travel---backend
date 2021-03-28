package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.UserConversation;

import java.util.List;

public interface UserConversationService {
    UserConversation create (UserConversation userConversation);
    List<UserConversation> findAll();
}
