package com.example.recrecipe;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//intent용
import android.content.Intent;

//이 밑으로 추가
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    
    //추가된 변수
    private List<String> list;              // 데이터를 넣은 리스트 변수
    private ListView listView;              // 검색을 보여줄 리스트 변수
    private EditText editSearch;            // 검색어를 입력할 input 창
    private SearchAdapter adapter;          // 리스트뷰에 연결할 adapter
    private ArrayList<String> Arraylist;
    private ListView recipelist;
    private ListViewAdapter adapterForRecipelist;
    
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipelist);


        //추가
        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);


        //리스트를 생성
        list = new ArrayList<String>();

        //검색에 사용할 데이터를 미리 저장
        settingList();

        //리스트의 모든 데이터를 arraylist에 복사한다.
        //list 복사본을 만든다.

        Arraylist = new ArrayList<String>();
        Arraylist.addAll(list);

        //리스트에 연동될 adapter 생성
        adapter = new SearchAdapter(list, this);

        // 커스텀 리스트 adapter 생성
        adapterForRecipelist = new ListViewAdapter();

        //커스텀 리스트 참조 및 adapter 달기
        recipelist = (ListView) findViewById(R.id.recipelist);
        recipelist.setAdapter(adapterForRecipelist);

        //커스텀 리스트 아이템 추가
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.salad),"BOX","Account Box Black 36dp");
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.sandwich),"BOX","Account Circle Black 36dp");
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.soup),"BOX","Assignment Ind Black 36dp");
        // 커스텀 뷰 이벤트 처리
        recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //get item
                RecipeListViewItem item = (RecipeListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitle();
                String descStr = item.getDesc();
                Drawable iconDrawable = item.getIcon();

            }
        });


        //리스트뷰에 adapter 연결
        listView.setAdapter(adapter);
        listView.bringToFront();

        //input창에 검색어를 입력시 "addTextChangedListenver"이벤트 리스터를 정의
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //input창에 문자를 입력할때마다 호출
                //search 메소드를 호출
                String text = editSearch.getText().toString();
                search(text);
            }
        });
        // 처음에 over된 리스트 안보이게 하기
        listView.setVisibility(View.GONE);
        // 리스트 뷰 클릭시 글씨를 검색창에 띄우기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String data = list.get(position);
                editSearch.setText(data);
            }
        });

    list.clear();
    }

    //검색을 수행하는 메소드

    public void search(String charText) {

        //문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        //문자 입력이 없을 때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            //검색 하지 않을 때 리스트 뷰가 보이지 않게 한다.
            listView.setVisibility(View.GONE);
            //list.addAll(Arraylist);
        }
        //문자를 입력할때
        else {

            //리스트의 모든 데이터를 검색
            for (int i = 0; i < Arraylist.size(); i++)
            {
                //arraylist의 모든 데이터에 입력받은 단어(charTxt)가 포함되어 있으면 true를 반환
                if(Arraylist.get(i).toLowerCase().contains(charText))
                {
                    //검색된 데이터를 리스트에 추가
                    list.add(Arraylist.get(i));
                    // 검색 시 리스트뷰가 보이게 만든다.
                    listView.setVisibility(View.VISIBLE);
                }
            }
        }
        //리스트 데이터가 변경되었으므로 adapter를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    //검색 버튼 클릭 시 검색 실시되도록
    public void btn_Click(View view)
    {
        EditText editText = (EditText)findViewById(R.id.editSearch);
        String Text = editText.getText().toString();
        Intent intent = new Intent(RecipeList.this, Recipe.class);
        intent.putExtra("Text",Text);
        startActivity(intent);

    }

    //검색에 사용될 데이터를 리스트에 추가
    private void settingList(){
        list.add("kimchi");
        list.add("soup");
        list.add("ramen");
        list.add("비빔밥");
        list.add("sandwich");
        list.add("볶음밥");
        list.add("고추장찌개");
        list.add("salad");
        list.add("피자");
        list.add("탕수육");
        list.add("꽁치찌개");
        list.add("간장밥");
        list.add("고등어조림");
        list.add("갈치구이");
    }
}
