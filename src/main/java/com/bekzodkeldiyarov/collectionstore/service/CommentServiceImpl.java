package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Comment;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.CommentRepository;
import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ItemService itemService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, ItemService itemService, UserService userService) {
        this.commentRepository = commentRepository;
        this.itemService = itemService;
        this.userService = userService;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment save(Long itemId, String username, String commentText) {
        User user = userService.findByUsername(username);
        Item item = itemService.findItemById(itemId);
        Comment commentToSave = Comment.builder().item(item).text(commentText).user(user).build();
        Comment savedComment = commentRepository.save(commentToSave);
        return savedComment;
    }
}
