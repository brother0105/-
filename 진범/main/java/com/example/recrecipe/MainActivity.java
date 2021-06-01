package com.example.recrecipe;

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
        Intent intent = new Intent(this, TestR.class);
        startActivity(intent);
    }
    public void onButtonClick2(View v) {
        Intent intent = new Intent(this, RecipeList.class);
        startActivity(intent);
    }

    public void onButtonClick3(View v) {
        Intent intent = new Intent(this, Testinternet.class);
        startActivity(intent);
    }

    public void onButtonClick4(View v) {
        Intent intent = new Intent(this, TestreceivefromInternet.class);
        startActivity(intent);
    }

    public void onButtonClick5(View v) {
        Intent intent = new Intent(this, CopyRecipelist.class);
        startActivity(intent);
    }

    public void onButtonClick6(View v) {
        Intent intent = new Intent(this, TestApi.class);
        startActivity(intent);
    }

    public void onButtonClick7(View v) {
        Intent intent = new Intent(this, TestUpdate.class);
        startActivity(intent);
    }

    public void EventGotofav(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


    public void SearchAdapter(){}

}