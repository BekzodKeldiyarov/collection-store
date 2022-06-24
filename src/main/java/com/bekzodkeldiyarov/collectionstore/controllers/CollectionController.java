package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class CollectionController {
    private final CollectionService collectionService;
    private final UserService userService;
    private final AttributeService attributeService;
    private final ItemService itemService;

    public CollectionController(CollectionService collectionService, UserService userService, AttributeService attributeService, ItemService itemService) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.attributeService = attributeService;
        this.itemService = itemService;
    }

    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        List<CollectionCommand> collections = collectionService.getAllCollectionCommands();
        model.addAttribute("collections", collections);
        return "admin/collections/list";
    }

    @GetMapping("/collections/add")
    public String getAddNewCollectionPage(Model model, CollectionCommand collectionCommand) {
        model.addAttribute("collection", collectionCommand);
        return "admin/collections/add";
    }

    @PostMapping("/collections/add")
    public String addNewCollection(@ModelAttribute("collection") CollectionCommand collectionCommand) {
        collectionCommand.setUser(userService.findByUsername("admin"));
        collectionService.saveCollectionCommand(collectionCommand);
        return "redirect:/admin/collections";
    }

    @GetMapping("/collections/view/{id}")
    public String getUpdateCollectionPage(@PathVariable Long id, Model model) {
        CollectionCommand collectionCommand = collectionService.findCollectionCommandById(id);
        List<AttributeCommand> attributes = attributeService.getAllAttributesOfCollection(collectionCommand.getId());
        List<ItemCommand> items = itemService.getAllItemsOfCollection(id);
        model.addAttribute("collection", collectionCommand);
        model.addAttribute("attributes", attributes);
        model.addAttribute("items", items);
        return "admin/collections/single-collection";
    }


}
