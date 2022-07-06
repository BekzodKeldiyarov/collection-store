package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Comment;

public interface CommentService {
    Comment save(Comment comment);

    Comment save(Long itemId, String username, String commentText);
}
