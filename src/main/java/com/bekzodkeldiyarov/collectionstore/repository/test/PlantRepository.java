package com.bekzodkeldiyarov.collectionstore.repository.test;

import com.bekzodkeldiyarov.collectionstore.model.Plant;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends SearchRepository<Plant, Long> {
}

