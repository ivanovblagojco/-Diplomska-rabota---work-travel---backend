package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;
import com.diplomska.backend.repository.ApplicationRepository;
import com.diplomska.backend.service.interfaces.ApplicationService;
import com.diplomska.backend.service.interfaces.PostService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostService postService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, PostService postService) {
        this.applicationRepository = applicationRepository;
        this.postService = postService;
    }

    @Override
    public Application createApplication(ApplicationHelper application) {
        Application applicationDB = new Application();

        applicationDB.setName(application.getName());
        applicationDB.setSurname(application.getSurname());
        applicationDB.setPhone(application.getPhone());
        applicationDB.setCountry(application.getCountry());
        applicationDB.setCity(application.getCity());
        applicationDB.setCitizenship(application.getCitizenship());
        applicationDB.setPost(postService.getById(application.getPost_id()));
        return this.applicationRepository.save(applicationDB);
    }
}
