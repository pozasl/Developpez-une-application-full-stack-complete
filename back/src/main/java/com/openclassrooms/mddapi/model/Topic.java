package com.openclassrooms.mddapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("topics")
public class Topic {

    private String ref;
    private String name;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getRef() {
        return ref;
    }

    public String getName() {
        return name;
    }

}
