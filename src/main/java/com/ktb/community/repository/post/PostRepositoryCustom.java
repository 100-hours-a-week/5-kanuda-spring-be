package com.ktb.community.repository.post;

import com.ktb.community.domain.dto.PostDTO;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostDTO> findAllPost();
    PostDTO findPostById(Long id);
    void updatePost(PostDTO postDTO);
}
