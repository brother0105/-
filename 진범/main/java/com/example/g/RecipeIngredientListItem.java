package com.example.g;
// 레시피 리스트의 커스텀 리스트뷰의 layout을 위한 클래스

import android.graphics.drawable.Drawable;

public class RecipeIngredientListItem {
    private String ing_name;
    private String ing_vol ;
    private String ing_type ;

    public void setName(String name) {
        ing_name = name ;
    }
    public void setVol(String vol) {
        ing_vol= vol ;
    }
    public void setType(String type) {
        ing_type = type ;
    }

    public String getName() {
        return this.ing_name ;
    }
    public String getVol() {
        return this.ing_vol ;
    }
    public String getType() {
        return this.ing_type ;
    }
}



