package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Like;

public interface LikeService {
    Like save(Like like);

    void like(Long id, String username);

    void dislike(Long id, String username);
}
