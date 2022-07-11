package com.bekzodkeldiyarov.collectionstore.config;

import com.bekzodkeldiyarov.collectionstore.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final EntityManager entityManager;

    public List<Item> getPostBasedOnWord(String word) {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Item.class)
                .get();

        Query query = qb.keyword()
                .onFields("name", "collection.name", "collection.description")
                .matching(word)
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager
                .createFullTextQuery(query, Item.class);
        return (List<Item>) fullTextQuery.getResultList();
    }


}