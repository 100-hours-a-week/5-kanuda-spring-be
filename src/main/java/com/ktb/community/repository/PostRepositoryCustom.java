package com.ktb.community.repository;

import com.ktb.community.domain.dto.PostDTO;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostDTO> findAllPost();
    PostDTO findPostById(Long id);
    void updatePost(PostDTO postDTO);
}
