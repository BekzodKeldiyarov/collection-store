package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    @Query("select c from Collection c where c.user.id = ?1")
    List<Collection> findAllByUserId(Long id);
}
