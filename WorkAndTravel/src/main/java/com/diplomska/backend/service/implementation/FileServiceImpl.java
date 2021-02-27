package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.FileNotFoundException;
import com.diplomska.backend.model.File;
import com.diplomska.backend.repository.FileRepository;
import com.diplomska.backend.service.interfaces.FileService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File create(File file) {
        return this.fileRepository.save(file);
    }

    @Override
    public File update(File file) {
        return this.fileRepository.save(file);
    }

    @Override
    public File findById(Long id) {
        return this.fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    @Override
    public List<File> findAll() {
        return this.fileRepository.findAll();
    }
}
