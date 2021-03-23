package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.CommentHelperFront;
import com.diplomska.backend.model.Comment;

import java.util.List;

public interface CommentService {
    Comment create (CommentHelperFront comment);
    Comment update (Comment comment);
    Comment findById (Long id);
    List<Comment> findAll();
}
