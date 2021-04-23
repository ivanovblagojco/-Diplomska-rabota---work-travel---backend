package com.diplomska.backend.web;

import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.model.User;
import com.diplomska.backend.service.interfaces.PostService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    @PostMapping("/createPost")
    public Post createPost (@RequestParam(value = "file", required = false)MultipartFile file, @RequestParam("id")Long id, @RequestParam("title")String title, @RequestParam("description") String description) throws IOException {
        Post post;
        if(id!=-1){
            post = postService.getById(id);
        }else {
            post=new Post();
        }
        post.setTitle(title);
        post.setDescription(description);
        return this.postService.create(post, file);
    }

    @GetMapping(value = {"/getAllPosts/{page}/{size}", "/getAllPosts/{place}/{page}/{size}"})
    public Page<PostHelper> getAllPosts (@PathVariable(required = false) String place, @PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts;
        if(place==null){
            posts = this.postService.findAll();
        }else {
            posts = this.postService.findAll().stream().filter(p->p.getPlace().equals(place)).collect(Collectors.toList());
        }

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

    @GetMapping(value = {"/getAllPostsFromUsers/{page}/{size}","/getAllPostsFromUsers/{place}/{page}/{size}"})
    public Page<PostHelper> getAllPostsFromUsers (@PathVariable(required = false) String place, @PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts;
        if(place==null){
            posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(false)).collect(Collectors.toList());
        }else {
            posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(false) && p.getPlace().equals(place)).collect(Collectors.toList());
        }

        posts = posts.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }

    @GetMapping(value = {"/getAllPostsFromAgency/{page}/{size}","/getAllPostsFromAgency/{place}/{page}/{size}"})
    public Page<PostHelper> getAllPostsFromAgency (@PathVariable(required = false)String place, @PathVariable int page, @PathVariable int size) {
        List<PostHelper> posts;
        if(place==null){
            posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(true)).collect(Collectors.toList());
        }else {
            posts = this.postService.findAll().stream().filter(p->p.getFrom_agency().equals(true) && p.getPlace().equals(place)).collect(Collectors.toList());
        }
        posts = posts.stream().sorted((first, second) -> -first.getId().compareTo(second.getId())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }
    @GetMapping("/getLoggedUserPosts/{page}/{size}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public Page<PostHelper> getLoggedUserPosts(@PathVariable int page, @PathVariable int size){
        User user = userService.getLoggedUser();

        List<PostHelper> posts = this.postService.findAll().stream().filter(p->p.getCreator().equals(user.getEmail())).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int startIdx = Math.min((int)pageable.getOffset(), posts.size());
        int endIdx = Math.min(startIdx + pageable.getPageSize(), posts.size());
        return new PageImpl<>(posts.subList(startIdx, endIdx),pageable,posts.size());
    }

    @DeleteMapping("/deletePost/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'AGENCY')")
    public void deletePost(@PathVariable Long id){
        postService.delete(id);
    }
}
