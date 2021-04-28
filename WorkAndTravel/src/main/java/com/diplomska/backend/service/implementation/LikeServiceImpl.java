package com.diplomska.backend.service.implementation;

import com.diplomska.backend.helpers.LikeHelper;
import com.diplomska.backend.model.Like;
import com.diplomska.backend.repository.LikeRepository;
import com.diplomska.backend.service.interfaces.LikeService;
import com.diplomska.backend.service.interfaces.LocationService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final LocationService locationService;
    private final UserService userService;
    public LikeServiceImpl(LikeRepository likeRepository, LocationService locationService, UserService userService) {
        this.likeRepository = likeRepository;
        this.locationService = locationService;
        this.userService = userService;
    }


    @Override
    public void likeOrDislike(LikeHelper like) {
        Like likeDB;
        if(this.likeRepository.findLikeForUserAndLocation(userService.findByEmail(like.getLiker()).getId(), like.getLocation_id())!=null){
            likeDB =this.likeRepository.findLikeForUserAndLocation(userService.findByEmail(like.getLiker()).getId(), like.getLocation_id());
            this.likeRepository.deleteById(likeDB.getId());
        }
        else {
            likeDB = new Like();

            likeDB.setLocation(locationService.findById(like.getLocation_id()));
            likeDB.setUser(userService.findByEmail(like.getLiker()));
            likeRepository.save(likeDB);
        }
    }

    @Override
    public Like findLikeForUserAndLocation(Long user_id, Long location_id) {
        return this.likeRepository.findLikeForUserAndLocation(user_id, location_id);
    }
}
