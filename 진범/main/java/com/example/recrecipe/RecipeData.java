package com.example.recrecipe;

public class RecipeData {
    private String id = "";
    private String num = "";
    private String discription = "";
    private String image_url = "";
    private String tip = "";


    public String get_id() {
        return id;
    }

    public String get_num() {
        return num;
    }

    public String get_discription() {
        return discription;
    }

    public String get_image_url() {
        return image_url;
    }

    public String get_tip() {
        return tip;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public void set_num(String num) {
        this.num = num;
    }

    public void set_discription(String discription) {
        this.discription = discription;
    }

    public void set_url(String image_url) {
        this.image_url = image_url;
    }

    public void set_tip(String tip) {
        this.tip = tip;
    }

}
