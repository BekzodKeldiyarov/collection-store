package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    public String getAddNewItemPage(@PathVariable Long collectionId, Model model, Principal principal) {
        log.info(principal + "");
        ItemCommand itemCommand = itemService.getNewItemCommandInstance(collectionId);
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("item", itemCommand);
        model.addAttribute("pageName", "Add Item");
        return "admin/items/add";
    }

    @PostMapping("/collections/{collectionId}/items/add")
    public String postNewItemToCollection(@Valid ItemCommand itemCommand, BindingResult result, @RequestParam String[] selectedTags) {
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
        }
        log.info(Arrays.toString(selectedTags));
        ItemCommand itemCommandToSave = itemService.bindTagsToItemCommand(itemCommand, selectedTags);
        ItemCommand savedItemCommand = itemService.saveItemCommand(itemCommandToSave);
        return "redirect:/dashboard/collections/" + savedItemCommand.getCollection().getId();
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}/edit")
    public String getUpdateItemPage(@PathVariable Long collectionId, @PathVariable Long itemId, Model model) {
        ItemCommand itemCommand = itemService.findItemCommandById(itemId);
        model.addAttribute("tags", tagService.getAllTags());
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

    @GetMapping("/items/tags/{id}")
    public String getItemsByTag(@PathVariable Long id, Model model) {
        Tag tag = tagService.findById(id);
        List<ItemCommand> allItems = itemService.getAllItems();
        List<ItemCommand> itemsContainsTag = new ArrayList<>();
        for (ItemCommand itemCommand : allItems) {
            if (itemCommand.getTags().contains(tag)) {
                itemsContainsTag.add(itemCommand);
            }
        }
        model.addAttribute("items", itemsContainsTag);
        model.addAttribute("pageName", tag.getName());

        return "admin/items/list";
    }

}
