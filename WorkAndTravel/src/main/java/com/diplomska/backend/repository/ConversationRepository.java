package com.diplomska.backend.repository;

import com.diplomska.backend.model.Comment;
import com.diplomska.backend.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("select c from Conversation c where c.name=:name")
    Conversation findByName(String name);
}
