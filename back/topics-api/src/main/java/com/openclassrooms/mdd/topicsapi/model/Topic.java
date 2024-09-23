package com.openclassrooms.mdd.topicsapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("topics")
public class Topic {

    private String ref;
    private String name;
    private String description;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
