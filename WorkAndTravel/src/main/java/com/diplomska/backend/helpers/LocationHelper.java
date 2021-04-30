package com.diplomska.backend.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationHelper {
    private Long id;
    private String country;
    private String city;
    private String creator;
    private Integer likes;
    private Boolean is_liked;
}
