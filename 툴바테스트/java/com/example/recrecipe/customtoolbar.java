package com.example.recrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class customtoolbar extends AppCompatActivity implements View.OnClickListener {//activity들의 기본이 되는 형태로

    private ViewGroup mainLayout;
    private FrameLayout viewLayout;
    private FrameLayout sideLayout;

    private Context mContext=customtoolbar.this;

    private boolean isMenushow=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity);


    }

    @Override
    public void setContentView(int LayoutResID){
        RelativeLayout fullview = (RelativeLayout) getLayoutInflater().inflate(R.layout.base_activity,null);
        FrameLayout activityContainer = (FrameLayout) fullview.findViewById(R.id.view_main);
        getLayoutInflater().inflate(LayoutResID,activityContainer,true);
        super.setContentView(fullview);

        init();
        addSideView();

    }

    protected void init(){

        ImageView sidemenu = (ImageView) findViewById(R.id.side_menu);
        Button titleback = (Button) findViewById(R.id.toolbar_back);
        TextView toolbartitle = (TextView) findViewById(R.id.toolbar_title);

        sidemenu.setOnClickListener(this);
        titleback.setOnClickListener(this);
        toolbartitle.setOnClickListener(this);

        mainLayout = findViewById(R.id.fullview);
        viewLayout = findViewById(R.id.FL_silde);
        sideLayout = findViewById(R.id.view_sildebar);


    }

    public void addSideView(){

        sidebar s_b= new sidebar(mContext);
        sideLayout.addView(s_b);

        viewLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }

        });

        s_b.setEventListener(new sidebar.EventListener() {
            @Override
            public void infoclick() {
                Intent intent = new Intent(getApplicationContext(),Myinfo.class);
                startActivity(intent);
            }

            @Override
            public void recipeclick() {
                Intent intent = new Intent(getApplicationContext(),RecipeList.class);
                startActivity(intent);
            }

            @Override
            public void favclick() {
                Intent intent = new Intent(getApplicationContext(),favorite.class);
                startActivity(intent);
            }

            @Override
            public void cancleclick() {
                closeMenu();
            }
        });
    }

    @Override
    public void onClick(View view){


        Toast.makeText(getApplicationContext(),"클릭 이벤트 발동",Toast.LENGTH_LONG);

        switch (view.getId()){
            case R.id.side_menu:
                Toast.makeText(getApplicationContext(),"side menu",Toast.LENGTH_LONG);
                showMenu();
                break;
            case R.id.toolbar_back:
                finish();
                break;


            case R.id.toolbar_title:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;


        }

    }

    @Override
    public void onBackPressed(){
        if(isMenushow)
            closeMenu();
        else{
            finish();
        }
    }



    public void showMenu(){
        isMenushow=true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);

    }



    public void closeMenu(){
        isMenushow=false;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_hidden);
        sideLayout.startAnimation(slide);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        },450);
    }

}
