package com.example.g;
// 레시피안의 레시피 리스트의 커스텀 리스트뷰의 layout을 위한 클래스


public class RealRecipeListItem {
    private String r_url;
    private String r_explain ;
    private String r_tip ;

    public void setUrl(String url) {
        r_url = url ;
    }
    public void setExp(String explain) {
        r_explain= explain ;
    }
    public void setTip(String tip) {
        r_tip = tip ;
    }

    public String getUrl() {
        return this.r_url ;
    }
    public String getExp() {
        return this.r_explain ;
    }
    public String getTip() {
        return this.r_tip ;
    }
}



