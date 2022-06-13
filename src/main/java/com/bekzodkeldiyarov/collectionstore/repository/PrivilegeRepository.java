package com.bekzodkeldiyarov.collectionstore.repository;

import com.bekzodkeldiyarov.collectionstore.model.Privilege;
import com.bekzodkeldiyarov.collectionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
