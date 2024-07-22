package org.albert.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@MongoEntity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author
{
    private ObjectId id;
    private String firstName;
    private String secondName;
}

// Already comes with methods for interacting with the database.
//public class Author extends PanacheMongoEntity
//{
//}
