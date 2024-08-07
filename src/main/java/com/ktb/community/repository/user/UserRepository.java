package com.ktb.community.repository.user;

import com.ktb.community.domain.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void updatePassword(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsById(Long id);
}
