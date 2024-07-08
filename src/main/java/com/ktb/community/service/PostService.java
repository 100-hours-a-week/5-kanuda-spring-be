package com.ktb.community.service;

import com.ktb.community.domain.dto.CommentDto;
import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.entity.Comment;
import com.ktb.community.domain.entity.User;
import com.ktb.community.repository.comment.CommentRepository;
import com.ktb.community.repository.post.PostRepository;
import com.ktb.community.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
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

    public void createPost(PostDTO postDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }
        postDTO.setUserId(user.getId());
        postRepository.save(postDTO.toEntity());
    }

    @Transactional
    public void updatePost(PostDTO postDTO) {
        postRepository.updatePost(postDTO);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not exist");
        }
        return commentRepository.findAllComment(postId);
    }

    public void createComment(CommentDto commentDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }
        commentDto.setUserId(user.getId());
        commentRepository.save(commentDto.toEntity());
    }

    @Transactional
    public void updateComment(Comment comment) {
        commentRepository.update(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public boolean checkPostWriter(Long postId, String userEmail) {
        User writer = userRepository.findByEmail(userEmail);
        PostDTO post = postRepository.findPostById(postId);

        if(Objects.equals(post.getUserId(), writer.getId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCommentWriter(Long commentId, String userEmail) {
        User writer = userRepository.findByEmail(userEmail);
        CommentDto commentDto = commentRepository.findCommentById(commentId);

        if(Objects.equals(commentDto.getUserId(), writer.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
