package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.CommentNotFoundException;
import com.diplomska.backend.model.Comment;
import com.diplomska.backend.repository.CommentRepository;
import com.diplomska.backend.service.interfaces.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment create(Comment comment) {
        return this.commentRepository.save(comment);
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
