package com.example.learning;
// 레시피 리스트의 커스텀 리스트뷰의 layout을 위한 클래스

import android.graphics.drawable.Drawable;

public class CopyRecipeListViewItem {
    private String iconDrawable;
    private String titleStr ;
    private String descStr ;

    public void setIcon(String  icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public String getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}



