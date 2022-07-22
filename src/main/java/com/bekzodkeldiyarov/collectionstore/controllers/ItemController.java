package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.Tag;
import com.bekzodkeldiyarov.collectionstore.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@Slf4j
@Api(description = "Items data")
public class ItemController {
    private final ItemService itemService;
    private final TagService tagService;
    private final CollectionService collectionService;

    public ItemController(ItemService itemService, TagService tagService, CollectionService collectionService) {
        this.itemService = itemService;
        this.tagService = tagService;
        this.collectionService = collectionService;
    }

    @GetMapping("/collections/{collectionsId}/items")
    public ResponseEntity<List<Item>> getAllItems(@PathVariable Long collectionsId) {
        List<Item> items = itemService.getItemsOfCollectionId(collectionsId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Long collectionId, @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findItemById(itemId));
    }

    @GetMapping("/collections/{collectionId}/items/add")
    public ResponseEntity<Item> addItem(@PathVariable Long collectionId) {

        Item item = itemService.getNewItemInstance(collectionId);

        return ResponseEntity.ok(item);
    }

    @PostMapping("/collections/{collectionId}/items/add")
    public ResponseEntity<Item> postItem(@PathVariable Long collectionId, @RequestBody Item item, @RequestParam(required = false) String[] selectedTags) {
//        Item itemToSave = itemService.bindTagsToItem(item, selectedTags);
        Collection collection = collectionService.findCollectionById(collectionId);
        collection.addItemToCollection(item);
        log.info("Saving ");
        Item savedItem = itemService.save(item);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}/edit")
    public String updateItem(@PathVariable Long collectionId, @PathVariable Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("item", item);
        model.addAttribute("pageName", "Edit Item");
        return "admin/items/add";
    }

    @GetMapping("/collections/{collectionId}/items/{itemId}/delete")
    public String deleteItem(@PathVariable Long collectionId, @PathVariable Long itemId, Model model) {
        itemService.deleteById(itemId);
        return "redirect:/dashboard/collections/" + collectionId;
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
