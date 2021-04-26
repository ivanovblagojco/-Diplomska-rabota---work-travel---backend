package com.diplomska.backend.web;

import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;
import com.diplomska.backend.model.Contact;
import com.diplomska.backend.service.interfaces.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasAnyRole('AGENCY')")
    @GetMapping("/getAllApplicationsForAgency/{page}/{size}")
    public Page<Application> getAllApplicationsForAgency(@PathVariable int page, @PathVariable int size){
        List<Application> list = this.applicationService.getAllApplicationForAgency();

        list = list.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), list.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startIdx, endIdx),pageable,list.size());
    }

    @PreAuthorize("hasAnyRole('AGENCY')")
    @GetMapping("/getApplication/{id}")
    public Application getAllApplicationsForAgency(@PathVariable Long id) {
        return this.applicationService.findById(id);
    }
}
