package com.ktb.community.controller;

import com.ktb.community.auth.CustomUserDetails;
import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.entity.Comment;
import com.ktb.community.domain.entity.Post;
import com.ktb.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@ModelAttribute PostDTO postDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String userEmail = userDetails.getUsername();
            postService.createPost(postDTO, userEmail);
            return new ResponseEntity<>("Post registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        try {
            PostDTO post = postService.getPostById(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            post.setId(id);
            postService.updatePost(post);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check-writer/post/{postId}")
    public ResponseEntity<String> checkPostWriter(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String userEmail = userDetails.getUsername();
            if(postService.checkPostWriter(postId, userEmail)) {
                return new ResponseEntity<>("same user", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("작성자가 아니면 권한이 없습니다.", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // comment
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        try {
            comment.setPostId(postId);
            postService.createComment(comment);
            return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = postService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        try {
            Comment comment = postService.getCommentById(commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody Comment comment) {
        try {
            comment.setId(commentId);
            postService.updateComment(comment);
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            postService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
