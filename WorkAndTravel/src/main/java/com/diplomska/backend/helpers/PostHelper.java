package com.diplomska.backend.helpers;

import com.diplomska.backend.model.File;
import com.diplomska.backend.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostHelper {
    private Post post;
    private File file;
}
