package com.example.demo_update.respository;

import com.example.demo_update.dto.Articles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends MongoRepository<Articles,String> {
    @Query("{"+"name: ?0"+"}")
    Articles findArticlesByName(String name);
    @Query("{" + "name: ?0"+ "  }")
    Articles updateArticlesByName(String id, String name);

}
