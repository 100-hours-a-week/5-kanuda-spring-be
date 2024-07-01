package com.ktb.community.domain.dto;

import com.ktb.community.domain.entity.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String image;
    private Long viewCnt;
    private Long likeCnt;
    private Long commentCnt;
    private LocalDateTime time;
    private String userNickname;
    private String userImage;

    @QueryProjection
    public PostDTO(Long id, Long userId, String title, String content, String image, Long viewCnt, Long likeCnt, Long commentCnt, LocalDateTime time, String userNickname, String userImage) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.time = time;
        this.userNickname = userNickname;
        this.userImage = userImage;
    }

    public Post toEntity() {
        return new Post(id, userId, title, content, image, viewCnt, likeCnt, commentCnt, time);
    }

    public static PostDTO toDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .viewCnt(post.getViewCnt())
                .likeCnt(post.getLikeCnt())
                .commentCnt(post.getCommentCnt())
                .time(post.getTime())
                .build();
    }
}
