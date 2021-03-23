package com.diplomska.backend.service.interfaces;


import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Post create(Post postF, MultipartFile fileF) throws IOException;
    Post update (Post post);
    PostHelper findById (Long id);
    Post getById (Long id);
    List<PostHelper> findAll();
}
