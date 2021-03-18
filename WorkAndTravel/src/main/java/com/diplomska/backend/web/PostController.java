package com.diplomska.backend.web;

import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.service.interfaces.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/createPost")
    public Post createPost (@RequestBody PostHelper postHelper){
        return this.postService.create(postHelper);
    }
}
