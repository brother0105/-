package com.example.recrecipe;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//intent용
import android.content.Intent;

//이 밑으로 추가
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Myfavnview extends AppCompatActivity {

    private List<String> list;
    private SearchAdapter adapter;
    private ArrayList<String> Arraylist;
    private ListView favlist;
    private ListViewAdapter adapterForfav;
    private ListView viewlist;
    private ListViewAdapter adapterForview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfavnview);

        list = new ArrayList<String>();
        Arraylist = new ArrayList<String>();
        Arraylist.addAll(list);

        adapter = new SearchAdapter(list, this);
        adapterForfav = new ListViewAdapter();

        favlist = (ListView) findViewById(R.id.Myfav);
        favlist.setAdapter(adapterForfav);

        viewlist = (ListView) findViewById(R.id.myview);
        viewlist.setAdapter(adapterForview);


        //커스텀 뷰 이벤트 처리에 대해 질문 필요. 길게 누르고 그 것에 대한 메모수정, 삭제 다이얼로그를 띄우게하려면 어떻게 해야하는가.


        list.clear();
    }

    public void onBackButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), "돌아가기 버튼을 눌렀어요.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void EventGotofav(View v){
        Intent intent = new Intent(getApplicationContext(), Myfavnview.class);
        startActivity(intent);
    }

    public void EventGotomyinfo(View v){
        Intent intent = new Intent(getApplicationContext(), Myinfo.class);
        startActivity(intent);
    }

}


