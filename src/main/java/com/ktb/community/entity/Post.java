package com.ktb.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String image;

    @Column(name = "like_cnt", nullable = false)
    private Long likeCnt;

    @Column(name = "comment_cnt", nullable = false)
    private Long commentCnt;

    @Column(name = "view_cnt", nullable = false)
    private Long viewCnt;

    @Column
    private LocalDateTime time;
    private String userNickname;
    private String userImage;
}
