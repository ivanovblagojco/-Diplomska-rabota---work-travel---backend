package com.diplomska.backend.web;

import com.diplomska.backend.helpers.CommentHelper;
import com.diplomska.backend.helpers.CommentHelperFront;
import com.diplomska.backend.model.Comment;
import com.diplomska.backend.service.interfaces.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/getAllComments/{page}/{size}/{post_id}")
    public Page<CommentHelper> getAllCommentsForPost(@PathVariable int page, @PathVariable int size, @PathVariable Long post_id){
        List<CommentHelper> comments =  this.commentService.findAll().stream().filter(c->c.getPost().getId().equals(post_id)).map(Comment::getAsCommentHelper).collect(Collectors.toList());

        comments = comments.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), comments.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), comments.size());
        return new PageImpl<>(comments.subList(startIdx, endIdx),pageable,comments.size());

    }
    @DeleteMapping("deleteComment/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public void deleteComment(@PathVariable Long id){
        this.commentService.deleteById(id);
    }
}
