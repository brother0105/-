package com.example.recrecipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.EventListener;

public class sidebar extends RelativeLayout implements View.OnClickListener {

    public EventListener listener;

    public void setEventListener(EventListener l){
        listener = l;
    }

    public interface EventListener {

        void infoclick();//나의 정보 클릭 이벤트
        void recipeclick();//레시피 클릭 이벤트
        void favclick();//즐겨찾기 클릭 이벤트
        void cancleclick();
    }

    public sidebar(Context context){
        this(context, null);
        init();
    }

    public sidebar(Context context, AttributeSet attri){
        super(context, attri);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.sidebar, this, true);

        findViewById(R.id.side_btn_cancel).setOnClickListener(this);
        findViewById(R.id.side_bar_favorite).setOnClickListener(this);
        findViewById(R.id.side_bar_myinfo).setOnClickListener(this);
        findViewById(R.id.side_bar_recipe).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        switch(view.getId()){
            case R.id.side_btn_cancel:

                listener.cancleclick();
                break;
            case R.id.side_bar_favorite:
                listener.favclick();
                break;
            case R.id.side_bar_myinfo:
                listener.infoclick();
                break;
            case R.id.side_bar_recipe:
                listener.recipeclick();
                break;

            default:
                break;
        }
    }


}
