package com.ktb.community.repository;

import com.ktb.community.domain.dto.PostDTO;
import com.ktb.community.domain.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostDTO> findAllPost();

    PostDTO findPostById(Long id);

    public void update(Post post);
}
