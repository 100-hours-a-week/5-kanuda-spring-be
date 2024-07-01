package com.ktb.community.repository;

import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.dto.QPostDTO;
import com.ktb.community.domain.entity.Post;
import com.ktb.community.domain.entity.QPost;
import com.ktb.community.domain.entity.QUser;
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
    public void update(Post post) {
        jpaQueryFactory.update(QPost.post)
                .set(qPost.title, post.getTitle())
                .set(qPost.content, post.getContent())
                .set(qPost.image, post.getImage())
                .where(qPost.id.eq(post.getId()))
                .execute();
    }

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public List<Post> findAll() {
//        String sql =
//                "SELECT " +
//                    "posts.id, " +
//                    "users.nickname as userNickname, " +
//                    "users.image as userImage, " +
//                    "posts.title, " +
//                    "posts.like_cnt, " +
//                    "posts.comment_cnt, " +
//                    "posts.view_cnt, " +
//                    "posts.time " +
//                "FROM posts " +
//                "INNER JOIN users " +
//                "ON posts.user_id = users.id";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
//    }
//
//    @Override
//    public Post findById(Long id) {
//        String sql =
//                "SELECT " +
//                    "posts.id, " +
//                    "users.nickname as userNickname, " +
//                    "users.image as userImage, " +
//                    "posts.title, " +
//                    "posts.content, " +
//                    "posts.image, " +
//                    "posts.like_cnt, " +
//                    "posts.comment_cnt, " +
//                    "posts.view_cnt, " +
//                    "posts.time " +
//                "FROM posts " +
//                "INNER JOIN users " +
//                "ON posts.user_id = users.id " +
//                "WHERE posts.id = ? ";
//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Post.class));
//    }
//
//    @Override
//    public void save(Post post) {
//        String sql = "INSERT INTO posts " +
//                "(user_id, title, content, image, like_cnt, comment_cnt, view_cnt, time)" +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql,
//                post.getUserId(), post.getTime(),
//                post.getContent(), post.getImage(),
//                post.getLikeCnt(), post.getCommentCnt(),
//                post.getViewCnt(), post.getTime());
//    }
//
//    @Override
//    public void update(Post post) {
//        String sql = "UPDATE posts SET title = ?, content = ? image = ? WHERE id = ?";
//        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getImage(), post.getId());
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        String sql = "DELETE FROM posts WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        String sql = "SELECT COUNT(*) FROM posts WHERE id = ?";
//        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
//        return count != null && count > 0;
//    }
}
