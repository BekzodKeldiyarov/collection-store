package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.Like;
import com.bekzodkeldiyarov.collectionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
@Query("select l from likes l where l.user = ?1 and l.item = ?2")
Like findByUserAndItem(User user,Item item);
}
