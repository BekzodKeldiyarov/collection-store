package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.CommentCommand;
import com.bekzodkeldiyarov.collectionstore.model.Comment;
import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import com.bekzodkeldiyarov.collectionstore.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public CommentController(CommentService commentService, SimpMessagingTemplate simpMessagingTemplate) {
        this.commentService = commentService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{id}")
    public void addComment(@DestinationVariable Long id, CommentCommand comment, Authentication auth) {
        MyUserDetails userDetail = (MyUserDetails) auth.getPrincipal();
        Comment savedComment = commentService.save(id, userDetail.getUsername(), comment.getText());
        simpMessagingTemplate.convertAndSend("/topic/messages/" + id, savedComment);
    }
}
