package com.diplomska.backend.web;

import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.service.interfaces.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/createPost")
    public Post createPost (@RequestParam("file")MultipartFile file, @RequestParam("title")String title, @RequestParam("description") String description) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        return this.postService.create(post, file);
    }

    @GetMapping("/getAllPosts")
    public List<PostHelper> getAllPosts () {
        return this.postService.findAll();
    }

    @GetMapping("/getPost/{id}")
    public PostHelper getPost (@PathVariable Long id) {
        return this.postService.findById(id);
    }

    @GetMapping("/getLastThreePosts")
    public List<PostHelper> getLastThreePosts() {
        return this.postService.findAll().subList(0,3);
    }
}
