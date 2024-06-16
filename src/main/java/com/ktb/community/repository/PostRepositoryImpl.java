package com.ktb.community.repository;

import com.ktb.community.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public Post findById(Long id) {
        String sql = "SELCET * FROM posts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO posts " +
                "(user_id, title, content, image, like_cnt, comment_cnt, view_cnt, time)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                post.getUserId(), post.getTime(),
                post.getContent(), post.getImage(),
                post.getLikeCnt(), post.getCommentCnt(),
                post.getViewCnt(), post.getTime());
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ? image = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getImage(), post.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
