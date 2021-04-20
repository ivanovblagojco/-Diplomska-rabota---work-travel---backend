package com.diplomska.backend.service.interfaces;


import com.diplomska.backend.model.File;

import java.util.List;

public interface FileService {
    File create (File file);
    File update (File file);
    File findById (Long id);
    List<File> findAll();
    void deleteById(Long id);

}
