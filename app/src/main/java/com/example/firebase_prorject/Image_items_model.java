package com.example.firebase_prorject;

import org.jetbrains.annotations.NotNull;

public class Image_items_model {

    public  String name, uri;

    public Image_items_model(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public Image_items_model() {}

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
