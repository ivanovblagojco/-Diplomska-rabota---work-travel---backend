package com.diplomska.backend.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostHelper implements Comparable<PostHelper>{
    private Long id;
    private String title;
    private String description;
    private String date_created;
    private String mime_type;
    private String bytes;
    private Boolean from_agency;
    private String creator;

    @Override
    public int compareTo(PostHelper o) {
        return this.getId().compareTo(o.getId());
    }
}
