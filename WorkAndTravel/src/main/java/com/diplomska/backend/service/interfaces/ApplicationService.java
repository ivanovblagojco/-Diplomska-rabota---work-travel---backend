package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.ApplicationHelper;
import com.diplomska.backend.model.Application;

public interface ApplicationService {
    Application createApplication(ApplicationHelper application);
}
