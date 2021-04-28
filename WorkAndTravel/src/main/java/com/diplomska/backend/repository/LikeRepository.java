package com.diplomska.backend.repository;

import com.diplomska.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where l.user.id=:user_id and l.location.id=:location_id")
    Like findLikeForUserAndLocation(Long user_id, Long location_id);

    @Modifying
    @Transactional
    @Query("delete from File f where f.id=:id")
    void deleteById(Long id);
}
