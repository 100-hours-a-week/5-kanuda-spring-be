package com.ktb.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
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

//    private String userNickname;
//    private String userImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    public Post(Long id, Long userId, String title, String content, String image,
                Long likeCnt, Long viewCnt, Long commentCnt, LocalDateTime time) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
        this.commentCnt = commentCnt;
        this.time = time;
    }
}
