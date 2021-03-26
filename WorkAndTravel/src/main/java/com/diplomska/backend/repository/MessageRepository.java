package com.diplomska.backend.repository;

import com.diplomska.backend.model.File;
import com.diplomska.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message, Long> {
}
