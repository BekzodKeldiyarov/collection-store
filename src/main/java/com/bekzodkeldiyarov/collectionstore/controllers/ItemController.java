package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/dashboard")
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final TagService tagService;

    public ItemController(ItemService itemService, TagService tagService) {
        this.itemService = itemService;
        this.tagService = tagService;
    }

    @GetMapping("/collections/{collectionId}/items/add")
    public String getAddNewItemPage(@PathVariable Long collectionId, Model model) {
        Item item = itemService.getNewItemInstance(collectionId);
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("item", item);
        model.addAttribute("pageName", "Add Item");
        return "admin/items/add";
    }

    @PostMapping("/collections/{collectionId}/items/add")
    public String postNewItemToCollection(@Valid Item item, BindingResult result, @RequestParam(required = false) String[] selectedTags) {
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
        }
        Item itemToSave = itemService.bindTagsToItem(item, selectedTags);
        Item savedItem = itemService.save(itemToSave);
        return "redirect:/dashboard/collections/" + savedItem.getCollection().getId();
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}/edit")
    public String getUpdateItemPage(@PathVariable Long collectionId, @PathVariable Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("item", item);
        model.addAttribute("pageName", "Edit Item");
        return "admin/items/add";
    }


    @GetMapping("/items/tags/{id}")
    public String getItemsByTag(@PathVariable Long id, Model model) {
        Tag tag = tagService.findById(id);
        List<Item> allItems = itemService.getAllItems();
        List<Item> itemsContainsTag = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getTags().contains(tag)) {
                itemsContainsTag.add(item);
            }
        }
        model.addAttribute("items", itemsContainsTag);
        model.addAttribute("pageName", tag.getName());

        return "admin/items/list";
    }

}
