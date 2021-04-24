package com.diplomska.backend.web;

import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;
import com.diplomska.backend.service.interfaces.ApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/createApplication")
    public Application createApplication(@RequestBody ApplicationHelper application){
        return this.applicationService.createApplication(application);
    }

}
