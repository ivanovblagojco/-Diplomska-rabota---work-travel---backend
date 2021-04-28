package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;

import java.util.List;

public interface LocationService {
    Location create (LocationHelper location);
    List<Location> findAll();
    Location findById(Long id);
}
