package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.Conversation;

public interface ConversationService {
    Conversation create (Conversation conversation);
    Conversation findByName(String name);
}
