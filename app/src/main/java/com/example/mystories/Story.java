package com.example.mystories;

import android.graphics.Bitmap;

public class Story {
    private String id;
    private String title;
    private String content;
    private String created_at;
    private Bitmap img;
    public Story(String id, String title, String content, Bitmap img, String created_at){
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
        this.created_at = created_at;

    }
    public String getId(){
        return this.id;
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
    public String getCreated_at(){
        return this.created_at;
    }
}
