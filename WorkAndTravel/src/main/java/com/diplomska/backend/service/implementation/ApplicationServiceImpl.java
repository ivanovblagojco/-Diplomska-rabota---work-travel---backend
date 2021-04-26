package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.ApplicationNotFoundException;
import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;
import com.diplomska.backend.repository.ApplicationRepository;
import com.diplomska.backend.service.interfaces.ApplicationService;
import com.diplomska.backend.service.interfaces.PostService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostService postService;
    private final UserService userService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, PostService postService, UserService userService) {
        this.applicationRepository = applicationRepository;
        this.postService = postService;
        this.userService = userService;
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

    @Override
    public List<Application> getAllApplicationForAgency() {
        return this.applicationRepository.findAll().stream().filter(a->a.getPost().getUser().equals(userService.getLoggedUser())).collect(Collectors.toList());
    }

    @Override
    public Application findById(Long id) {
        return this.applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
    }
}
