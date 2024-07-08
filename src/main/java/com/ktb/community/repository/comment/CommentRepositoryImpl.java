package com.ktb.community.repository.comment;

import com.ktb.community.domain.dto.CommentDto;
import com.ktb.community.domain.dto.QCommentDto;
import com.ktb.community.domain.entity.Comment;
import com.ktb.community.domain.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ktb.community.domain.entity.QComment.comment;
import static com.ktb.community.domain.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComment qComment = comment;

    @Override
    public List<CommentDto> findAllComment(Long postId) {
        return jpaQueryFactory
                .select(new QCommentDto(
                        comment.id, comment.postId, comment.userId,
                        comment.content, comment.time,
                        user.nickname, user.image))
                .from(comment)
                .join(comment.user, user)
                .fetch();
    }

    @Override
    public CommentDto findCommentById(Long commentId) {
        return jpaQueryFactory
                .select(new QCommentDto(comment.id, comment.postId, comment.userId,
                        comment.content, comment.time, user.nickname, user.image))
                .from(comment)
                .join(comment.user, user)
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    @Override
    public void update(Comment comment) {
        jpaQueryFactory.update(QComment.comment)
                .set(qComment.content, comment.getContent())
                .where(qComment.id.eq(comment.getId()))
                .execute();
    }
}
