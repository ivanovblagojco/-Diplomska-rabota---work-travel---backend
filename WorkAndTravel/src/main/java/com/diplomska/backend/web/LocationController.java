package com.diplomska.backend.web;

import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;
import com.diplomska.backend.service.interfaces.LocationConverterService;
import com.diplomska.backend.service.interfaces.LocationService;
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
public class LocationController {
    private final LocationService locationService;
    private final LocationConverterService locationConverterService;
    public LocationController(LocationService locationService, LocationConverterService locationConverterService) {
        this.locationService = locationService;
        this.locationConverterService = locationConverterService;
    }

    @PostMapping("/createLocation")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public Location createLocation(@RequestBody LocationHelper location){
        return locationService.create(location);
    }

    @GetMapping("/getAllLocations/{email}/{page}/{size}")
    public Page<LocationHelper> getAllLocations(@PathVariable String email, @PathVariable int page, @PathVariable int size){
        List<Location> listL = locationService.findAll();

        List<LocationHelper> list = listL.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).map((Location location) -> locationConverterService.getAsLocationHelper(location, email)).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), list.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startIdx, endIdx),pageable,list.size());
    }
}
