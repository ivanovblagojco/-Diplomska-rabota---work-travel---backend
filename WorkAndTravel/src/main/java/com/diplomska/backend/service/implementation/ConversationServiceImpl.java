package com.diplomska.backend.service.implementation;

import com.diplomska.backend.model.Conversation;
import com.diplomska.backend.repository.ConversationRepository;
import com.diplomska.backend.service.interfaces.ConversationService;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Conversation create(Conversation conversation) {
        return this.conversationRepository.save(conversation);
    }

    @Override
    public Conversation findByName(String name) {
        return this.conversationRepository.findByName(name);
    }
}
