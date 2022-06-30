package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import com.bekzodkeldiyarov.collectionstore.repository.ItemAttributeValueRepository;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemAttributeValueService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final AttributeService attributeService;
    private final CollectionService collectionService;
    private final ItemAttributeValueService itemAttributeValueService;

    public ItemController(ItemService itemService, AttributeService attributeService, CollectionService collectionService, ItemAttributeValueService itemAttributeValueService) {
        this.itemService = itemService;
        this.attributeService = attributeService;
        this.collectionService = collectionService;
        this.itemAttributeValueService = itemAttributeValueService;
    }

    @GetMapping("/collections/{collectionId}/items/add")
    public String getAddNewItemPage(@PathVariable Long collectionId, Model model) {
        ItemCommand itemCommand = itemService.getNewItemCommandInstance(collectionId);
        model.addAttribute("item", itemCommand);
        model.addAttribute("pageName", "Add Item");
        return "admin/items/add";
    }

    @PostMapping("/collections/{collectionId}/items/add")
    public String postNewItemToCollection(@Valid ItemCommand itemCommand, BindingResult result) {
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
        }
        itemService.saveItemCommand(itemCommand);
        return "redirect:/admin/collections";
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}/edit")
    public String getUpdateItemPage(@PathVariable Long collectionId, @PathVariable Long itemId, Model model) {
        ItemCommand itemCommand = itemService.findItemCommandById(itemId);
        model.addAttribute("item", itemCommand);
        model.addAttribute("pageName", "Edit Item");
        return "admin/items/add";
    }

    @GetMapping("/items")
    public String getAllItemsOfUser(Model model) {
        List<ItemCommand> allItems = itemService.getAllItemsOfUser(3L);

        model.addAttribute("items", allItems);
        model.addAttribute("pageName", "All my items");
        return "admin/items/list";
    }


}
