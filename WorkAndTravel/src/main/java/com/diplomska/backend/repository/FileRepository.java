package com.diplomska.backend.repository;

import com.diplomska.backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
