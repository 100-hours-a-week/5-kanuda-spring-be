package com.ktb.community.controller;

import com.ktb.community.auth.CustomUserDetails;
import com.ktb.community.domain.dto.CommentDto;
import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.entity.Comment;
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
    public ResponseEntity<String> updatePost(@PathVariable Long id, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            postDTO.setId(id);
            postService.updatePost(postDTO);
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

    @GetMapping("/check-writer/comment/{commentId}")
    public ResponseEntity<String> checkCommentWriter(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String userEmail = userDetails.getUsername();
            if(postService.checkCommentWriter(commentId, userEmail)) {
                return new ResponseEntity<>("same user", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("작성자가 아니면 권한이 없습니다.", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // comment
    @PostMapping("/comments/{postId}")
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            System.out.println(commentDto);
            String userEmail = userDetails.getUsername();
            commentDto.setPostId(postId);
            postService.createComment(commentDto, userEmail);
            return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = postService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
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
