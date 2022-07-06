package com.bekzodkeldiyarov.collectionstore.commands;

import com.bekzodkeldiyarov.collectionstore.model.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCommand {
    private Long id;
    private String name;
    private Collection collection;


    List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();
    private List<Like> likes = new ArrayList<>();
}
