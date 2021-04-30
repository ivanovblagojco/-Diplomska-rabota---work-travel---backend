package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;
import com.diplomska.backend.service.interfaces.LocationConverterService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class LocationConverterServiceImpl implements LocationConverterService {

    @Override
    public LocationHelper getAsLocationHelper(Location location, String email) {
        LocationHelper locationHelper = new LocationHelper();

        locationHelper.setId(location.getId());
        locationHelper.setCity(location.getCity());
        locationHelper.setCountry(location.getCountry());
        locationHelper.setCreator(location.getUser().getEmail());
        locationHelper.setLikes(location.getLikes().size());
        locationHelper.setIs_liked(false);
        for(int i=0; i< location.getLikes().size(); i++){
            if(location.getLikes().get(i).getUser().getEmail().equals(email)){
                locationHelper.setIs_liked(true);
                break;
            }
        }
        return locationHelper;
    }
}
