package com.bekzodkeldiyarov.collectionstore.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@Component
@Slf4j
public class Indexer {
    private EntityManager entityManager;

    public Indexer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void indexData() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
            log.info("Indexed.........");
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }


        log.info("Data indexed...");

        log.info("Searching...");
    }
}
