package com.ktb.community.service;

import com.ktb.community.entity.Post;
import com.ktb.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        if(!postRepository.existsById(id)) {
            throw new RuntimeException("Post not exist");
        }
        return postRepository.findById(id);
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public void updatePost(Post post) {
        postRepository.update(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
