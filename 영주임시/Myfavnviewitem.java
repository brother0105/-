package com.example.learning;

import android.graphics.drawable.Drawable;

public class Myfavnviewitem {
    private int type;//타입구별. 0이면 fav 1이면 view

    private Drawable iconDrawable ;
    private String titleStr ;
    private String memoStr ;//fav면 memo
    private String datedata;//view면 date type은 string으로 하지만 memo와 달리 수정불가하도록

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setMemo(String memo) {
        memoStr = memo ;
    }
    public void setDate(String date){datedata=date;}
    public void setType(int a){type=a;}

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getmemo() {
        return this.memoStr ;
    }
    public String getDatedata(){return this.datedata;}
    public int getType(){return this.type;}
}
