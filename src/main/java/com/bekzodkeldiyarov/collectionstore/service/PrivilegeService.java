package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.Privilege;

public interface PrivilegeService {
    Privilege findByName(String name);

    Privilege save(Privilege privilege);
}
