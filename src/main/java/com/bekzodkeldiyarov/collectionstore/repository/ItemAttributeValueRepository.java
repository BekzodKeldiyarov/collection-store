package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.ItemAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemAttributeValueRepository extends JpaRepository<ItemAttributeValue, Long> {
    List<ItemAttributeValue> findByItemId(Long id);


}
