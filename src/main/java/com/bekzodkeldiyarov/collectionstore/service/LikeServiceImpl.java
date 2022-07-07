package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.Like;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.LikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ItemService itemService;

    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, ItemService itemService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void like(Long id, String username) {
        Like like = new Like();
        User user = userService.findByUsername(username);
        Item item = itemService.findItemById(id);

        user.getLikes().add(like);
        item.getLikes().add(like);
        like.setItem(item);
        like.setUser(user);
        userService.save(user);
        itemService.save(item);
        likeRepository.save(like);
    }

    @Override
    public void dislike(Long id, String username) {
        User user = userService.findByUsername(username);
        Item item = itemService.findItemById(id);
        Like like = likeRepository.findByUserAndItem(user, item);
        user.getLikes().remove(like);
        item.getLikes().remove(like);
        userService.save(user);
        itemService.save(item);
        likeRepository.delete(like);
    }
}
