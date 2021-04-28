package com.diplomska.backend.web;

import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;
import com.diplomska.backend.service.interfaces.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/createLocation")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public Location createLocation(LocationHelper location){
        return locationService.create(location);
    }

    @GetMapping("/getAllLocations/{page}/{size}")
    public Page<Location> getAllLocations(@PathVariable int page, @PathVariable int size){
        List<Location> list = locationService.findAll();

        list = list.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), list.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startIdx, endIdx),pageable,list.size());
    }
}
