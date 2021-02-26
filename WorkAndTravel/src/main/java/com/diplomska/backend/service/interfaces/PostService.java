package com.diplomska.backend.service.interfaces;


import com.diplomska.backend.model.Post;

import java.util.List;

public interface PostService {
    Post create (Post post);
    Post update (Post post);
    Post findById (Long id);
    List<Post> findAll();
}
