package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.CommentNotFoundException;
import com.diplomska.backend.helpers.CommentHelperFront;
import com.diplomska.backend.model.Comment;
import com.diplomska.backend.repository.CommentRepository;
import com.diplomska.backend.service.interfaces.CommentService;
import com.diplomska.backend.service.interfaces.PostService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Comment create(CommentHelperFront comment) {
        Comment commentDB = new Comment();
        commentDB.setDescription(comment.getDescription());
        commentDB.setPost(postService.getById(comment.getPost_id()));
        commentDB.setUser(userService.findByEmail(comment.getUser_email()));


        return this.commentRepository.save(commentDB);
    }

    @Override
    public Comment update(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }
}
