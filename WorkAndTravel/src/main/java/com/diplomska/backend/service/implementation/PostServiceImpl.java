package com.diplomska.backend.service.implementation;

import com.diplomska.backend.exceptions.PostNotFoundException;
import com.diplomska.backend.model.Post;
import com.diplomska.backend.repository.PostRepository;
import com.diplomska.backend.service.interfaces.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post create(Post post) {
        return this.postRepository.save(post);
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
