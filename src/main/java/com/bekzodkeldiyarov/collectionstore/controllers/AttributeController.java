package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.service.AttributeService;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AttributeController {
    private final AttributeService attributeService;
    private final CollectionService collectionService;
    private final UserService userService;

    public AttributeController(AttributeService attributeService, CollectionService collectionService, UserService userService) {
        this.attributeService = attributeService;
        this.collectionService = collectionService;
        this.userService = userService;
    }

    @GetMapping("/collections/{id}/attributes/add")
    public String addAttributeForCollection(@PathVariable Long id, Model model) {
        CollectionCommand collectionCommand = collectionService.findCollectionCommandById(id);
        model.addAttribute("collection", collectionCommand);
        return "admin/collections/add-attribute";
    }


    @PostMapping("/collections/{collectionId}/attributes/add")
    public String addNewAttribute(@PathVariable Long collectionId, HttpServletRequest request) {
        CollectionCommand collectionCommand = collectionService.findCollectionCommandById(collectionId);
        Set<AttributeCommand> attributeCommands = createAttributesFromHttpRequest(request);

        attributeService.bindAttributesToCollection(attributeCommands, collectionCommand);

        collectionService.save(collectionCommand);
        return "index";
    }


    private Set<AttributeCommand> createAttributesFromHttpRequest(HttpServletRequest request) {
        Set<AttributeCommand> attributes = new HashSet<>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String attributeNameKey = keys.nextElement();
            String attributeNameValue = request.getParameter(attributeNameKey);
            if (!attributeNameKey.contains("attribute_name")) {
                continue;
            }
            String attributeTypeKey = keys.nextElement();
            String attributeTypeValue = request.getParameter(attributeTypeKey);

            AttributeCommand attributeCommand = AttributeCommand.builder()
                    .attributeName(attributeNameValue)
                    .type(attributeTypeValue)
                    .build();
            attributes.add(attributeCommand);
        }
        return attributes;
    }
}
