package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.LocationNotFoundException;
import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;
import com.diplomska.backend.repository.LocationRepository;
import com.diplomska.backend.service.interfaces.LocationService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UserService userService;

    public LocationServiceImpl(LocationRepository locationRepository, UserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    @Override
    public Location create(LocationHelper location) {
        Location locationDB = new Location();

        locationDB.setCountry(location.getCountry());
        locationDB.setCountry(location.getCity());
        locationDB.setUser(userService.findByEmail(location.getCreator()));

        return locationRepository.save(locationDB);
    }

    @Override
    public List<Location> findAll() {
        return this.locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
    }
}
