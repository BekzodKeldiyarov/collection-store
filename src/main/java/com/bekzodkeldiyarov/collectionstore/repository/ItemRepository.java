package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.collection.id = ?1")
    List<Item> findByCollectionId(Long id);

    @Query("select i from Item i where i.collection.id = ?1")
    List<Item> findAllByCollectionId(Long collectionId);

    List<Item> findAllByOrderByIdDesc();
}
