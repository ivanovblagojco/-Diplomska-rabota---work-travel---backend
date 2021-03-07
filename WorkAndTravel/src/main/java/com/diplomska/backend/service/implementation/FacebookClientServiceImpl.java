package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.FacebookUser;
import com.diplomska.backend.service.interfaces.FacebookClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacebookClientServiceImpl implements FacebookClientService {
    private final String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";

   private final RestTemplate restTemplate;

    public FacebookClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public FacebookUser getUser(String accessToken) {
        var path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        var fields = "email,first_name,last_name,id,picture.width(720).height(720)";
        final Map<String, String> variables = new HashMap<>();
        variables.put("fields", fields);
        variables.put("redirect", "false");
        variables.put("access_token", accessToken);
        return restTemplate.getForObject(FACEBOOK_GRAPH_API_BASE + path, FacebookUser.class, variables);
    }
}
