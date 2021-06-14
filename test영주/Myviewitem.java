package com.example.recrecipe;

import android.graphics.drawable.Drawable;

public class Myviewitem {
    private int type;//타입구별. 0이면 fav 1이면 view

    private Drawable iconDrawable ;
    private String titleStr ;
    private String dateStr ;//fav면 memo

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDate(String date) {
        dateStr = date ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getdate() {
        return this.dateStr ;
    }
}