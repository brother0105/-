package com.example.learning;
// 레시피 리스트의 커스텀 리스트뷰의 layout을 위한 클래스

import android.graphics.drawable.Drawable;

public class RecipeListViewItem {
    private Drawable iconDrawable;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}



