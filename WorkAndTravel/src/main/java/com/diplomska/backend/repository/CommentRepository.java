package com.diplomska.backend.repository;

import com.diplomska.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.id=:id")
    void deleteById(Long id);
}
