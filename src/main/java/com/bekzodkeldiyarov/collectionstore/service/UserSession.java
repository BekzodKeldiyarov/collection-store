package com.bekzodkeldiyarov.collectionstore.service;

public interface UserSession {
    void expireSessionForNonActiveUsers();
}
