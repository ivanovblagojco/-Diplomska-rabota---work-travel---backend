package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.LikeHelper;
import com.diplomska.backend.model.Like;

public interface LikeService{
    void likeOrDislike (LikeHelper like);
    Like findLikeForUserAndLocation(Long user_id, Long location_id);

}
