package com.example.mystories;

import android.graphics.Bitmap;

public class Story {
    private String title;
    private String content;
    private Bitmap img;
    public Story(String title, String content, Bitmap img){
        this.title = title;
        this.content = content;
        this.img = img;
    }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public Bitmap getImg(){
        return this.img;
    }
}
