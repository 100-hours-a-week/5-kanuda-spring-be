package com.ktb.community.repository.comment;

import com.ktb.community.domain.dto.CommentDto;
import com.ktb.community.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentDto> findAllComment(Long postId);
    CommentDto findCommentById(Long commentId);
    void update(Comment comment);
}
