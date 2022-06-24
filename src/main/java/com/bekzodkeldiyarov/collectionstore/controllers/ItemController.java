package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final AttributeService attributeService;
    private final CollectionService collectionService;

    public ItemController(ItemService itemService, AttributeService attributeService, CollectionService collectionService) {
        this.itemService = itemService;
        this.attributeService = attributeService;
        this.collectionService = collectionService;
    }

    @GetMapping("/collections/{collectionId}/items/add")
    public String getNewItemToCollection(@PathVariable Long collectionId, Model model) {
        ItemCommand itemCommand = new ItemCommand();
        List<AttributeCommand> attributes = attributeService.getAllAttributesOfCollection(collectionId);
        CollectionCommand collection = collectionService.findCollectionCommandById(collectionId);
        model.addAttribute("collection", collection);
        model.addAttribute("attributes", attributes);
        model.addAttribute("item", itemCommand);

        return "admin/items/add";
    }

    @PostMapping("/collections/{collectionId}/items/add")
    public String postNewItemToCollection(@ModelAttribute("item") ItemCommand itemCommand,
                                          @PathVariable Long collectionId) {
        CollectionCommand collectionCommand = collectionService.findCollectionCommandById(collectionId);
        itemService.saveItemCommandAndBindCollectionCommand(itemCommand, collectionCommand);


        return "redirect:/admin/collections";
    }
}
