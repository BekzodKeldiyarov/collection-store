package com.bekzodkeldiyarov.collectionstore;

import com.bekzodkeldiyarov.collectionstore.config.Indexer;
import com.bekzodkeldiyarov.collectionstore.model.Plant;
import com.bekzodkeldiyarov.collectionstore.repository.test.PlantRepository;
import com.bekzodkeldiyarov.collectionstore.repository.test.SearchRepositoryImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = SearchRepositoryImpl.class)
public class CollectionStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionStoreApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeData(PlantRepository plantRepository) throws Exception {
        return (ApplicationArguments args) -> {
            List<Plant> plants = Arrays.asList(
                    new Plant("so balpine fir", "abies lasiocarpa", "pinaceae"),
                    new Plant("sour cherry", "prunus cerasus", "rosaceae"),
                    new Plant("asian pear", "pyrus pyrifolia", "rosaceae"),
                    new Plant("chinese witch hazel", "hamamelis mollis", "hamamelidaceae"),
                    new Plant("so lver maple", "acer saccharinum", "sapindaceae"),
                    new Plant("cucumber tree", "magnolia acuminata", "magnoliaceae"),
                    new Plant("korean rhododendron", "rhododendron mucronulatum", "ericaceae"),
                    new Plant("water lettuce", "pistia", "araceae"),
                    new Plant("so ssile oak", "quercus petraea", "fagaceae"),
                    new Plant("common fig", "ficus carica", "moraceae")
            );
            plantRepository.saveAll(plants);
        };
    }

    @Bean
    public ApplicationRunner buildIndex(Indexer indexer) throws Exception {
        return (ApplicationArguments args) -> {
            indexer.indexPersistedData("com.bekzodkeldiyarov.collectionstore.model.Item");
        };
    }
}
