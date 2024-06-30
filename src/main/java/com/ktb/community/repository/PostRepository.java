package com.ktb.community.repository;

import com.ktb.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

//    List<Post> findAll();
//    Post findById(Long id);
//    void save(Post post);
//    void update(Post post);
//    void deleteById(Long id);
//    boolean existsById(Long id);
}
