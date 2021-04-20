package com.diplomska.backend.repository;

import com.diplomska.backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FileRepository extends JpaRepository<File, Long> {
    @Modifying
    @Transactional
    @Query("delete from File f where f.id=:id")
    void deleteById(Long id);
}
