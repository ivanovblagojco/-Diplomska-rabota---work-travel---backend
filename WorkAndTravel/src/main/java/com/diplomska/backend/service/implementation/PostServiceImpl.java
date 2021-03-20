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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public Post create(Post post, MultipartFile fileF) throws IOException {
        post.setUser(userService.getLoggedUser());
        post = postRepository.save(post);

        File f = new File();
        f.setContent(fileF.getBytes());
        f.setUser(userService.getLoggedUser());
        f.setName(fileF.getName());
        f.setMime_type(fileF.getContentType());
        f.setPost(post);
        fileService.create(f);

        return post;
    }

    @Override
    public Post update(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public PostHelper findById(Long id) {
        return this.postRepository.findById(id).orElseThrow(PostNotFoundException::new).getAsPostHelper();
    }

    @Override
    public List<PostHelper> findAll() {
        List<PostHelper> posts =  this.postRepository.findAll().stream().map(Post::getAsPostHelper).collect(Collectors.toList());

        Collections.sort(posts, Collections.reverseOrder());

        return posts;
    }
}
