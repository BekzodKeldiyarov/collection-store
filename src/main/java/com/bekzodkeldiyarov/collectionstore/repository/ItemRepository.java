package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
