package com.diplomska.backend.web;

import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.service.interfaces.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @PostMapping("/createPost")
    public Post createPost (@RequestParam("file")MultipartFile file, @RequestParam("title")String title, @RequestParam("description") String description) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        return this.postService.create(post, file);
    }

    @GetMapping("/getAllPosts/{page}/{size}")
    public Page<PostHelper> getAllPosts (@PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts = this.postService.findAll();

        posts = posts.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }

    @GetMapping("/getPost/{id}")
    public PostHelper getPost (@PathVariable Long id) {
        return this.postService.findById(id);
    }

    @GetMapping("/getLastThreePosts")
    public List<PostHelper> getLastThreePosts() {
        return this.postService.findAll().subList(0,3);
    }

    @GetMapping("/getAllPostsFromUsers/{page}/{size}")
    public Page<PostHelper> getAllPostsFromUsers (@PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(false)).collect(Collectors.toList());

        posts = posts.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }

    @GetMapping("/getAllPostsFromAgency/{page}/{size}")
    public Page<PostHelper> getAllPostsFromAgency (@PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(true)).collect(Collectors.toList());

        posts = posts.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }
}
