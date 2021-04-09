package com.example.learning;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Recipe extends AppCompatActivity {

    TextView receiveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Intent intent = getIntent();
        String receivestr = intent.getExtras().getString("Text");
        receiveView = (TextView)findViewById(R.id.food_name);
        receiveView.setText(receivestr);

    }
}
