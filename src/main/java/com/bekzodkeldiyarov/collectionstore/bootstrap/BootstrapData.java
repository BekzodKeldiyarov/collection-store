package com.bekzodkeldiyarov.collectionstore.bootstrap;

import com.bekzodkeldiyarov.collectionstore.config.search.IndexingService;
import com.bekzodkeldiyarov.collectionstore.config.search.SearchService;
import com.bekzodkeldiyarov.collectionstore.model.*;
import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.repository.ItemAttributeValueRepository;
import com.bekzodkeldiyarov.collectionstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
@Slf4j
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RoleService roleService;
    private final CollectionService collectionService;
    private final ItemService itemService;
    private final AttributeService attributeService;
    private final TagService tagService;
    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    private SearchService searchService;
    @Autowired
    private IndexingService indexingService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ItemAttributeValueRepository itemAttributeValueRepository;


    public BootstrapData(UserService userService, RoleService roleService, CollectionService collectionService, ItemService itemService, AttributeService attributeService, TagService tagService, CommentService commentService, LikeService likeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.collectionService = collectionService;
        this.itemService = itemService;
        this.attributeService = attributeService;
        this.tagService = tagService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnabled(true);


        User user = new User();
        user.setUsername("bekzod");
        user.setEmail("bekzod@gmail.com");
        user.setPassword(passwordEncoder.encode("bekzod"));
        user.setEnabled(true);
        userService.save(user);

        User blockedUser = new User();
        blockedUser.setUsername("user");
        blockedUser.setEmail("user@gmail.com");
        blockedUser.setPassword(passwordEncoder.encode("user"));
        blockedUser.setEnabled(false);
        userService.save(blockedUser);


        Collection collectionCommand = Collection.builder()
                .name("My book")
                .description("My books collection")
                .user(admin)
                .items(new HashSet<>())
                .attributes(new LinkedHashSet<>()).build();

        Item item = new Item();
        item.setName("Robinzon");


        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.getUsers().add(admin);

        admin.getRoles().add(role);


        userService.save(admin);
        roleService.save(role);
        collectionService.save(collectionCommand);
        ItemAttributeValue itemAttributeValue = new ItemAttributeValue();
        Collection collection = Collection.builder().name("New Collection").description("Collection for many-to-many relationship").user(admin).attributes(new HashSet<>()).build();

        Attribute attribute = Attribute.builder().attributeName("Test").type("string").collection(collection).build();
        collection.getAttributes().add(attribute);

//        Tag tag = Tag.builder().name("books").build();
//        item.getTags().add(tag);
//        tag.getItems().add(item);
//
//        tagService.save(Tag.builder().name("wines").build());
//        tagService.save(Tag.builder().name("phones").build());
//        tagService.save(Tag.builder().name("mac").build());
//        tagService.save(Tag.builder().name("summer").build());

//        itemAttributeValue.setAttribute(attribute);
//        itemAttributeValue.setItem(item);
//
//        item.getItemAttributeValues().add(itemAttributeValue);
//        item.setCollection(collection);
//
//        attribute.getItemAttributeValues().add(itemAttributeValue);


//        Like like = new Like();
//        like.setUser(user);
//        like.setItem(item);
//
//        user.getLikes().add(like);
//        item.getLikes().add(like);
//
//        Like like2 = new Like();
//        like2.setUser(blockedUser);
//        like2.setItem(item);
//        blockedUser.getLikes().add(like2);
//        item.getLikes().add(like2);
//
//        likeService.save(like);
//        likeService.save(like2);

        //todo make relation user->likes->item many-to-many instead of one-to-many and one-to-many
//        commentService.save(Comment.builder().user(admin).text("test comment").item(item).build());
//        commentService.save(Comment.builder().user(admin).text("second test comment").item(item).build());
//        commentService.save(Comment.builder().user(admin).text("third test comment").item(item).build());
        //todo problems with saving comments -- save the transient instance before flushing : com.bekzodkeldiyarov.collectionstore.model.Comment.item -> com.bekzodkeldiyarov.collectionstore.model.Item; nested exception is java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : com.bekzodkeldiyarov.collectionstore.model.Comment.item -> com.bekzodkeldiyarov.collectionstore.model.Item
//        tagService.save(tag);
        collectionService.save(collection);
        log.info("Data Bootstrapped");
    }
}
