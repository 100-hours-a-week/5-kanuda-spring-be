package com.ktb.community.repository;

import com.ktb.community.entity.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post findById(Long id);
    void save(Post post);
    void update(Post post);
    void deleteById(Long id);
}
