package com.example.makeafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void EventGotofav(View v){
        Intent intent = new Intent(getApplicationContext(), Myfavnview.class);
        startActivity(intent);
    }

}