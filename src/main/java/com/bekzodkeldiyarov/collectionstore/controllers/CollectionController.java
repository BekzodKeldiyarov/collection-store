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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/api/dashboard")
public class CollectionController {
    private final CollectionService collectionService;
    private final UserService userService;
    private final AttributeService attributeService;
    private final UserSecurity userSecurity;

    public CollectionController(CollectionService collectionService, UserService userService, AttributeService attributeService, UserSecurity userSecurity) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.attributeService = attributeService;
        this.userSecurity = userSecurity;
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> getCollections(Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        boolean isUserAdmin = userSecurity.userIsAdmin(user.getUsername());
        List<Collection> collections = collectionService.getAllCollectionsOfUser(isUserAdmin, user.getUsername());
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/collections/add")
    public ResponseEntity<Collection> addCollection(Model model, Collection collection) {
        model.addAttribute("action", "Add");
        return ResponseEntity.ok(collection);
    }

    @PostMapping("/collections/add")
    public ResponseEntity<Collection> postCollection(@RequestBody Collection collection, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        collection.setUser(user);
        Collection savedCollection = collectionService.save(collection);
        return ResponseEntity.ok(savedCollection);
    }


    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable Long id, Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        boolean isUserAdmin = userSecurity.userIsAdmin(user.getUsername());
        Collection collection = collectionService.findCollectionById(id);
        if (user.getUsername().equals(collection.getUser().getUsername()) || isUserAdmin) {
            return ResponseEntity.ok(collection);
        } else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/collections/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId) {
        collectionService.deleteCollectionById(collectionId);
    }

    @GetMapping("/collections/{collectionId}/edit")
    public String editCollection(@PathVariable Long collectionId, Model model) {
        Collection collection = collectionService.findCollectionById(collectionId);
        model.addAttribute("collection", collection);
        return "admin/collections/edit";
    }

    @PutMapping("/collections/{collectionId}")
    public ResponseEntity<Collection> updateCollection(@RequestBody Collection collection) {
        log.info("Collection in controller " + collection.getAttributes());
        Collection savedCollection = collectionService.save(collection);
        return ResponseEntity.ok(savedCollection);
    }
}
