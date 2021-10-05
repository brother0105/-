package com.example.recrecipe;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

public class apporecipe extends customtoolbar {//apporecipe 화면

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apporecipe);

        findViewById(R.id.appo_sort_recipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewChange(1);
            }
        });

        findViewById(R.id.appo_sort_ingredient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewChange(2);
            }
        });

        ViewChange(1);
    }

    private void ViewChange(int a){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(a) {
            case 1:
                appo_sort_recipe changerecipe = new appo_sort_recipe();
                transaction.replace(R.id.apporecipeframe, changerecipe);
                transaction.commit();
                break;

            case 2:
                appo_sort_ingredient changeingredient = new appo_sort_ingredient();
                transaction.replace(R.id.apporecipeframe, changeingredient);
                transaction.commit();
                break;

            default:
                break;
        }

    }



}
