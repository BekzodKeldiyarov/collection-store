package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Comment;
import com.bekzodkeldiyarov.collectionstore.model.Item;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        model.addAttribute("indexMessage", "last-items");
        model.addAttribute("items", itemService.getAllItemsSortByLastAdded());
        model.addAttribute("collections", collectionService.getBiggestCollections());
        model.addAttribute("tags", tagService.getAllTags());
        return "index";
    }

    @GetMapping("/items/{id}")
    public String getItemPage(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id);
        model.addAttribute("item", item);
        model.addAttribute("comment", new Comment());
        model.addAttribute("items", itemService.getAllItems()); //todo not all items but last n items
        model.addAttribute("collections", collectionService.getBiggestCollections());
        model.addAttribute("tags", tagService.getAllTags());
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

    @GetMapping("/tags/{tagId}")
    public String getItemsByTag(@PathVariable Long tagId, Model model) {
        Tag tag = tagService.findById(tagId);
        Set<Item> items = tag.getItems();
        model.addAttribute("pageName", tag.getName());
        model.addAttribute("items", items);
        model.addAttribute("collections", collectionService.getBiggestCollections());
        model.addAttribute("tags", tagService.getAllTags());
        return "index";
    }

    @GetMapping("/collections/{collectionId}")
    public String getItemsByCollection(@PathVariable Long collectionId, Model model) {
        Collection collection = collectionService.findCollectionById(collectionId);
        Set<Item> items = collection.getItems();
        model.addAttribute("pageName", collection.getName());
        model.addAttribute("items", items);
        model.addAttribute("collections", collectionService.getBiggestCollections());
        model.addAttribute("tags", tagService.getAllTags());
        return "index";
    }

    @GetMapping("/users/{userId}")
    public String getCollectionsAndItemsOfUser(@PathVariable Long userId, Model model) {
        List<Collection> allCollectionsOfUser = collectionService.getAllCollectionsOfUser(userId);
        log.info(allCollectionsOfUser.size() + "");
        model.addAttribute("pageName", allCollectionsOfUser.get(0).getUser().getUsername());
        model.addAttribute("collectionsOfUser", allCollectionsOfUser);
        model.addAttribute("collections", collectionService.getBiggestCollections());
        model.addAttribute("tags", tagService.getAllTags());
        return "collections";
    }


}
