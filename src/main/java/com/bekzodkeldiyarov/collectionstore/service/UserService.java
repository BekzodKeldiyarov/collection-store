package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.model.User;

public interface UserService{
    User findByUsername(String username);
    User save(User user);
}
