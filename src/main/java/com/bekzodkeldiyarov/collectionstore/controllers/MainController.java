package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final ItemService itemService;
    private final CollectionService collectionService;
    private final TagService tagService;

    public MainController(ItemService itemService, CollectionService collectionService, TagService tagService) {
        this.itemService = itemService;
        this.collectionService = collectionService;
        this.tagService = tagService;
    }


    @GetMapping("")
    public String index(Model model) {
        List<ItemCommand> items = itemService.getAllItems(); //todo not all items but last n items
        List<CollectionCommand> collections = collectionService.getAllCollectionCommands(); //todo not all collections but biggest 5 collections
        List<Tag> tags = tagService.getAllTags();

        model.addAttribute("items", items);
        model.addAttribute("collections", collections);
        model.addAttribute("tags", tags);
        return "index";
    }
}
