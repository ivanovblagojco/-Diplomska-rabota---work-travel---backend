package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;

import java.util.List;

public interface ApplicationService {
    Application createApplication(ApplicationHelper application);
    List<Application> getAllApplicationForAgency();
    Application findById(Long id);
}
