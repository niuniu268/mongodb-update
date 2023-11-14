package com.example.demo_update.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Articles {
    @Id
    private String id;
    private String name;


    public Articles () {
    }

    public Articles (String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获取
     * @return id
     */
    public String getId () {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId (String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return name
     */
    public String getName () {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName (String name) {
        this.name = name;
    }

    public String toString () {
        return "Articles{id = " + id + ", name = " + name + "}";
    }
}
