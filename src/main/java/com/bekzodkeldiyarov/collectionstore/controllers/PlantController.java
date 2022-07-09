package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.Plant;
import com.bekzodkeldiyarov.collectionstore.model.SearchRequestDTO;
import com.bekzodkeldiyarov.collectionstore.service.ItemService;
import com.bekzodkeldiyarov.collectionstore.service.PlantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plant")
public class PlantController {

    private ItemService itemService;
    private PlantService plantService;

    public PlantController(ItemService itemService, PlantService plantService) {
        this.itemService = itemService;
        this.plantService = plantService;
    }

    @GetMapping("/search")
    public List<Item> searchPlants(SearchRequestDTO searchRequestDTO) {

        log.info("Request for plant search received with data : " + searchRequestDTO);
        return  itemService.searchItems(searchRequestDTO.getText(), searchRequestDTO.getFields(), searchRequestDTO.getLimit());
    }
}