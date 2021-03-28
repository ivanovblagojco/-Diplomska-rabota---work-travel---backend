package com.diplomska.backend.service.implementation;


import com.diplomska.backend.model.UserConversation;
import com.diplomska.backend.repository.UserConversationRepository;
import com.diplomska.backend.service.interfaces.UserConversationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserConversationServiceImpl implements UserConversationService {
    private final UserConversationRepository userConversationRepository;

    public UserConversationServiceImpl(UserConversationRepository userConversationRepository) {
        this.userConversationRepository = userConversationRepository;
    }

    @Override
    public UserConversation create(UserConversation userConversation) {
        return this.userConversationRepository.save(userConversation);
    }

    @Override
    public List<UserConversation> findAll() {
        return this.userConversationRepository.findAll();
    }
}
