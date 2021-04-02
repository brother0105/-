package com.example.learning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View v) {
        Intent intent = new Intent(this, SubActivity01.class);
        startActivity(intent);
    }
    public void onButtonClick2(View v) {
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
    }


    public void SearchAdapter(){}

}