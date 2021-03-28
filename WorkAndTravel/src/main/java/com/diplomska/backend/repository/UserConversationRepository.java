package com.diplomska.backend.repository;

import com.diplomska.backend.model.UserConversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConversationRepository extends JpaRepository<UserConversation, Long> {
}
