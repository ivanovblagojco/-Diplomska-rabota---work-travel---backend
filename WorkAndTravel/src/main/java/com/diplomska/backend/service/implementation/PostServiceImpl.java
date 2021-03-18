package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.PostNotFoundException;
import com.diplomska.backend.helpers.PostHelper;
import com.diplomska.backend.model.File;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.repository.PostRepository;
import com.diplomska.backend.service.interfaces.FileService;
import com.diplomska.backend.service.interfaces.PostService;
import com.diplomska.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final FileService fileService;

    public PostServiceImpl(PostRepository postRepository, UserService userService, FileService fileService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public Post create(PostHelper postHelper) {

        Post post = new Post();
        post.setTitle(postHelper.getPost().getTitle());
        post.setDescription(postHelper.getPost().getDescription());
        post.setUser(userService.getLoggedUser());
        post = postRepository.save(post);

        File f = new File();
        f.setContent(postHelper.getFile().getContent());
        f.setUser(userService.getLoggedUser());
        f.setName(postHelper.getFile().getName());
        f.setPost(post);
        fileService.create(f);

        return post;
    }

    @Override
    public Post update(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return this.postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }
}
