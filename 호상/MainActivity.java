package com.example.recrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*
    public void onButton1Clicked(View v) {
        Toast.makeText(getApplicationContext(), "시작 버튼이 눌렸어요.", Toast.LENGTH_LONG).show();
    }
 */
    public void onButton1Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    public void onButton2Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), IngredientMenu.class);
        startActivity(intent);
    }
    public void onButton3Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(intent);
    }
    public void onButton4Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), IngredientModify.class);
        startActivity(intent);
    }

}