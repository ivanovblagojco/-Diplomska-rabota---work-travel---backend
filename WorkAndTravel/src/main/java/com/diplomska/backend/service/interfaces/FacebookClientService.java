package com.diplomska.backend.service.interfaces;


import com.diplomska.backend.helpers.FacebookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public interface FacebookClientService {
    FacebookUser getUser(String accessToken);
}
