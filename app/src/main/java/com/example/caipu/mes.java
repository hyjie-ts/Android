package com.example.caipu;

import android.graphics.Bitmap;

public class mes {
    private String name;
    private Bitmap imageId;
    public mes(String name, Bitmap imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public Bitmap getImageId() {
        return imageId;
    }
}