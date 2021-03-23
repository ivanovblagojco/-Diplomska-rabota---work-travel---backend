package com.diplomska.backend.web;

import com.diplomska.backend.helpers.CommentHelper;
import com.diplomska.backend.helpers.CommentHelperFront;
import com.diplomska.backend.model.Comment;
import com.diplomska.backend.service.interfaces.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @PostMapping("/createComment")
    public Comment createComment (@RequestBody CommentHelperFront commentHelperFront){
        return this.commentService.create(commentHelperFront);
    }

    @GetMapping("/getAllComments/{post_id}")
    public List<CommentHelper> getAllCommentsForPost(@PathVariable Long post_id){
        return this.commentService.findAll().stream().filter(c->c.getPost().getId().equals(post_id)).map(Comment::getAsCommentHelper).collect(Collectors.toList());
    }
}
