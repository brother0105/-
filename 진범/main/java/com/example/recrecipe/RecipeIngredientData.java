package com.example.recrecipe;

public class RecipeIngredientData {

    private String id = "";
    private String num = "";
    private String name = "";
    private String cap = "";
    private String tycode = "";
    private String tyname = "";


    public String get_id() {
        return id;
    }

    public String get_num() {
        return num;
    }

    public String get_name() {
        return name;
    }

    public String get_cap() {
        return cap;
    }

    public String get_tycode() {
        return tycode;
    }

    public void set_id(String id) {
        this.id = id;
    }

    public void set_num(String num) {
        this.num = num;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public void set_cap(String cap) {
        this.cap = cap;
    }

    public void set_tycode(String tycode) {
        this.tycode = tycode;
    }
}
