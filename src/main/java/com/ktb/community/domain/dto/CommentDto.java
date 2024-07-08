package com.ktb.community.domain.dto;

import com.ktb.community.domain.entity.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime time;
    private String userNickname;
    private String userImage;

    @QueryProjection
    public CommentDto(Long id, Long postId, Long userId, String content, LocalDateTime time, String userNickname, String userImage) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.time = time;
        this.userNickname = userNickname;
        this.userImage = userImage;
    }

    public Comment toEntity() {
        return new Comment(id, postId, userId, content, time);
    }
}
