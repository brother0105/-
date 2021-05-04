package com.example.learning;


public class TestpersonalData {
    private String member_id;
    private String member_name;
    private String member_ingredient;
    private String member_recipe;

    public String getMember_id() {
        return member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_ingredient() {
        return member_ingredient;
    }

    public String getMember_recipe() {
        return member_recipe;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_ingredient(String member_ingredient) {
        this.member_ingredient = member_ingredient;
    }

    public void setMember_recipe(String member_address) {
        this.member_recipe = member_address;
    }
}