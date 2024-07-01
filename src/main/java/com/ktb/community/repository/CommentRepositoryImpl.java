package com.ktb.community.repository;

import com.ktb.community.domain.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> findAll(Long postId) {
        String sql =
                "SELECT " +
                    "comments.id, " +
                    "posts.id as postId, " +
                    "users.id as userId, " +
                    "users.nickname as userNickname, " +
                    "users.image as userImage, " +
                    "comments.content, " +
                    "comments.time " +
                "FROM comments " +
                "INNER JOIN users " +
                "ON comments.user_id = users.id " +
                "INNER JOIN posts " +
                "ON comments.post_id = posts.id " +
                "WHERE posts.id = ?";
        return jdbcTemplate.query(sql, new Object[]{postId}, new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public Comment findById(Long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO comments (post_id, user_id, content, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, comment.getPostId(), comment.getUserId(), comment.getContent(), comment.getTime());
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET content = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM comments WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }
}
