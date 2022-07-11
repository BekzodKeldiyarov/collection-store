package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import com.bekzodkeldiyarov.collectionstore.security.UserSecurity;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final UserSecurity userSecurity;

    public CollectionController(CollectionService collectionService, UserService userService, AttributeService attributeService, ItemService itemService, UserSecurity userSecurity) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.attributeService = attributeService;
        this.itemService = itemService;
        this.userSecurity = userSecurity;
    }

    @GetMapping("/collections")
    public String getAllCollections(Authentication authentication, Model model) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        boolean isUserAdmin = userSecurity.userIsAdmin(user.getUsername());
        List<Collection> collections = collectionService.getAllCollectionsOfUser(isUserAdmin, user.getUsername());
        model.addAttribute("collections", collections);
        return "admin/collections/list";
    }

    @GetMapping("/collections/add")
    public String getAddNewCollectionPage(Model model, Collection collection) {
        model.addAttribute("collection", collection);
        model.addAttribute("action", "Add");
        return "admin/collections/add";
    }


    @PostMapping("/collections/add")
    public String addNewCollection(@ModelAttribute("collection") Collection collection, HttpServletRequest request, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        collection.setUser(user);
        Set<Attribute> newAttributes = attributeService.createAttributesFromHttpServletRequest(request);
        collection.setAttributes(newAttributes);
        collectionService.save(collection);
        for (Attribute attribute : newAttributes) {
            attribute.setCollection(collection);
            attributeService.save(attribute);
        }
        return "redirect:/dashboard/collections";
    }


    @GetMapping("/collections/{id}")
    public String getSingleCollectionPage(@PathVariable Long id, Model model, Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        boolean isUserAdmin = userSecurity.userIsAdmin(user.getUsername());
        Collection collection = collectionService.findCollectionById(id);
        if (user.getUsername().equals(collection.getUser().getUsername()) || isUserAdmin) {
            model.addAttribute("collection", collection);
        }
        return "admin/collections/single-collection";
    }

    @GetMapping("/collections/{collectionId}/delete")
    public String deleteCollection(@PathVariable Long collectionId) {
        collectionService.deleteCollectionById(collectionId);
        return "redirect:/dashboard/collections/";
    }

    @GetMapping("/collections/{collectionId}/edit")
    public String getEditPage(@PathVariable Long collectionId, Model model) {
        Collection collection = collectionService.findCollectionById(collectionId);
        model.addAttribute("collection", collection);
        return "admin/collections/edit";
    }

    @PostMapping("/collections/{collectionId}/edit")
    public String editCollection(@ModelAttribute("collection") Collection collection) {
        collectionService.save(collection);
        return "redirect:/dashboard/collections";
    }
}
