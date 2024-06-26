package com.ktb.community.service;

import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.entity.Comment;
import com.ktb.community.domain.entity.Post;
import com.ktb.community.repository.CommentRepository;
import com.ktb.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    public List<PostDTO> getAllPosts() {
        return postRepository.findAllPost();
    }

    public PostDTO getPostById(Long id) {
        if(!postRepository.existsById(id)) {
            throw new RuntimeException("Post not exist");
        }
        return postRepository.findPostById(id);
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

    public List<Comment> getCommentsByPostId(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not exist");
        }
        return commentRepository.findAll(postId);
    }

    public Comment getCommentById(Long id) {
        if(!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not exist");
        }
        return commentRepository.findById(id);
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.update(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
