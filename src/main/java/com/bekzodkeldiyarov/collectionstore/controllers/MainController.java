package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Comment;
import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.LikeService;
import com.bekzodkeldiyarov.collectionstore.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class MainController {
    private final ItemService itemService;
    private final CollectionService collectionService;
    private final TagService tagService;
    private final LikeService likeService;

    public MainController(ItemService itemService, CollectionService collectionService, TagService tagService, LikeService likeService) {
        this.itemService = itemService;
        this.collectionService = collectionService;
        this.tagService = tagService;
        this.likeService = likeService;
    }


    @GetMapping("")
    public String index(Model model) {
        List<ItemCommand> items = itemService.getAllItems(); //todo not all items but last n items
        List<Collection> collections = collectionService.getBiggestCollections();
        List<Tag> tags = tagService.getAllTags();

        model.addAttribute("items", items);
        model.addAttribute("collections", collections);
        model.addAttribute("tags", tags);
        return "index";
    }

    @GetMapping("/items/{id}")
    public String getItemPage(@PathVariable Long id, Model model) {
        ItemCommand itemCommand = itemService.findItemCommandById(id);
        model.addAttribute("item", itemCommand);
        model.addAttribute("comment", new Comment());
        return "item";
    }


    @RequestMapping(value = "/items/{id}/like", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void likeItem(@PathVariable Long id, Authentication auth) {
        MyUserDetails userDetail = (MyUserDetails) auth.getPrincipal();
        likeService.like(id, userDetail.getUsername());
    }

    @RequestMapping(value = "/items/{id}/dislike", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void dislikeItem(@PathVariable Long id, Authentication auth) {
        MyUserDetails userDetail = (MyUserDetails) auth.getPrincipal();
        likeService.dislike(id, userDetail.getUsername());
    }

    @GetMapping("/dashboard")
    public String getDashboardPage() {
        return "admin/index";
    }
}
