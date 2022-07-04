package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.commands.ItemCommand;
import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/dashboard")
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
    public String addNewCollection(@ModelAttribute("collection") CollectionCommand collectionCommand, HttpServletRequest request, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        collectionCommand.setUser(user);
        Set<Attribute> attributes = attributeService.createAttributesForCollectionFromHttpServletRequest(request);
        if (attributes.size() > 0) {
            collectionService.saveCollectionCommand(collectionCommand, attributes);
        } else {
            collectionService.saveCollectionCommand(collectionCommand);
        }
        return "redirect:/dashboard/collections";
    }

    @GetMapping("/collections/{id}")
    public String getUpdateCollectionPage(@PathVariable Long id, Model model) {
        CollectionCommand collectionCommand = collectionService.findCollectionCommandById(id);
        List<ItemCommand> items = itemService.getAllItemsOfCollection(id);
        model.addAttribute("collection", collectionCommand);
        if (items.size() > 0) {
            model.addAttribute("items", items);
        }
        return "admin/collections/single-collection";
    }

}
