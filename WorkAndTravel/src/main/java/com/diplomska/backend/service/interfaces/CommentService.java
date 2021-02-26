package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.Comment;
import com.diplomska.backend.model.Post;

import java.util.List;

public interface CommentService {
    Comment create (Comment comment);
    Comment update (Comment comment);
    Comment findById (Long id);
    List<Comment> findAll();
}
