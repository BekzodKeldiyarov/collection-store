package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class CollectionController {
    private final CollectionService collectionService;
    private final UserService userService;
    private final AttributeRepository attributeRepository;
    public CollectionController(CollectionService collectionService, UserService userService, AttributeRepository attributeRepository) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.attributeRepository = attributeRepository;
    }

    @GetMapping("/collections")
    public String getCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "admin/collections/list";
    }

    @GetMapping("/collections/add")
    public String getAddNewCollectionView(Model model) {
        Collection collection = new Collection();
        model.addAttribute("collection", collection);
        return "admin/collections/add";
    }

    @PostMapping("/collections/add")
    public String addNewCollection(@ModelAttribute("collection") Collection collection, HttpServletRequest request) {
        collection.setUser(userService.findByUsername("admin"));
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()) {
            String attributeNameKey = (String) keys.nextElement();
            String attributeNameValue = request.getParameter(attributeNameKey);
            if (!attributeNameKey.contains("attribute_name")) {
                continue;
            }
            String attributeTypeKey = (String) keys.nextElement();
            String attributeTypeValue = request.getParameter(attributeTypeKey);

            Attribute attribute = new Attribute();
            attribute.setAttributeName(attributeNameValue);
            attribute.setType(attributeTypeValue);
            attribute.setCollection(collection);
            collection.getAttributes().add(attribute);
            collectionService.save(collection);
            attributeRepository.save(attribute);
        }
        return "redirect:/admin/collections";
    }
}
