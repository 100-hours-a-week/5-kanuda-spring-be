package com.ktb.community.repository;

import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.dto.QPostDTO;
import com.ktb.community.domain.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ktb.community.domain.entity.QPost.post;
import static com.ktb.community.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = post;

    @Override
    public List<PostDTO> findAllPost() {
        return jpaQueryFactory
                .select(new QPostDTO(
                        post.id, post.userId, post.title,
                        post.content, post.image, post.viewCnt,
                        post.likeCnt, post.commentCnt, post.time,
                        user.nickname, user.image))
                .from(post)
                .join(post.user, user)
                .fetch();
    }

    @Override
    public PostDTO findPostById(Long id) {
        return jpaQueryFactory
                .select(new QPostDTO(post.id, post.userId, post.title,
                        post.content, post.image, post.viewCnt,
                        post.likeCnt, post.commentCnt, post.time,
                        user.nickname, user.image))
                .from(post)
                .join(post.user, user)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public void updatePost(PostDTO postDTO) {
        jpaQueryFactory.update(QPost.post)
                .set(qPost.title, postDTO.getTitle())
                .set(qPost.content, postDTO.getContent())
                .set(qPost.image, postDTO.getImage())
                .where(qPost.id.eq(postDTO.getId()))
                .execute();
    }
}
