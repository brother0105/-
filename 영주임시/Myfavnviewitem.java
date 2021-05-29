package com.example.learning;

import android.graphics.drawable.Drawable;

public class Myfavnviewitem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String memoStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String memo) {
        memoStr = memo ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getmemo() {
        return this.memoStr ;
    }
}