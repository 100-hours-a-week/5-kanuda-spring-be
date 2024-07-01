package com.ktb.community.repository;

import com.ktb.community.domain.entity.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll(Long postId);
    Comment findById(Long id);
    void save(Comment comment);
    void update(Comment comment);
    void deleteById(Long id);
    boolean existsById(Long id);
}
