package com.diplomska.backend.model;

import com.diplomska.backend.helpers.LocationHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="location", schema="wat")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<Like> likes;


    @JsonIgnore
    public LocationHelper getAsLocationHelper(){
        LocationHelper locationHelper = new LocationHelper();

        locationHelper.setId(id);
        locationHelper.setCity(city);
        locationHelper.setCountry(country);
        locationHelper.setCreator(user.getEmail());
        locationHelper.setLikes(likes.size());

        return locationHelper;
    }
}

