package com.example.g;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//intent용
import android.content.Intent;

//이 밑으로 추가
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CopyRecipelist extends AppCompatActivity {

    //추가된 변수
    private List<String> list;              // 데이터를 넣은 리스트 변수
    private ListView listView;              // 검색을 보여줄 리스트 변수
    private EditText editSearch;            // 검색어를 입력할 input 창
    private SearchAdapter adapter;          // 리스트뷰에 연결할 adapter
    private ArrayList<String> Arraylist;
    private ListView recipelist;
    private CopyListViewAdapter adapterForRecipelist;

    private ScrollView p;

    private static String IP_ADDRESS = "192.168.219.101";
    private static String TAG = "phptest";
    private String mJsonString;
    private TextView mTextViewResult;

    private String title = "salad";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copy_recipelist);



        //scroll
        p = (ScrollView) findViewById(R.id.mainview);


        //text
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        //추가
        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        //리스트를 생성
        list = new ArrayList<String>();

        //검색에 사용할 데이터를 미리 저장

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/testgetjson.php", "");

        //리스트의 모든 데이터를 arraylist에 복사한다.
        //list 복사본을 만든다.

        Arraylist = new ArrayList<String>();
        //Arraylist.addAll(list);

        //리스트에 연동될 adapter 생성
        adapter = new SearchAdapter(list, this);

        // 커스텀 리스트 adapter 생성
        adapterForRecipelist = new CopyListViewAdapter();

        //커스텀 리스트 참조 및 adapter 달기
        recipelist = (ListView) findViewById(R.id.recipelist);
        recipelist.setAdapter(adapterForRecipelist);

        //커스텀 리스트 아이템 추가
        /*adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.salad),"BOX","Account Box Black 36dp");
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.sandwich),"BOX","Account Circle Black 36dp");
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.soup),"BOX","Assignment Ind Black 36dp");
        adapterForRecipelist.addItem(ContextCompat.getDrawable(this,R.drawable.search_icon),"BBOX","a");
*/
        // 커스텀 뷰 이벤트 처리
        recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                //get item
                CopyRecipeListViewItem item = (CopyRecipeListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitle();
                String descStr = item.getDesc();
                String iconDrawable = item.getIcon();


                String Textincustom = item.getIcon();
                Intent intent = new Intent(CopyRecipelist.this, Recipe.class);
                intent.putExtra("Text",Textincustom);
                startActivity(intent);

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

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                p.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        recipelist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                p.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

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
        Intent intent = new Intent(CopyRecipelist.this, Recipe.class);
        intent.putExtra("Text",Text);
        startActivity(intent);

    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = ProgressDialog.show(CopyRecipelist.this,
                    "Please Wait", null, true, true);

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            mTextViewResult.setText(result);


            progressDialog.dismiss();


            Log.d(TAG, "response - " + result);

            if (result == null){
                mTextViewResult.setText(errorString);

            }
            else {

                mJsonString = result;
                showResult();

                Arraylist.addAll(list);
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_INGREDIENT = "ingredient";
        String TAG_RECIPE ="recipe";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String ingredient = item.getString(TAG_INGREDIENT);
                String recipe = item.getString(TAG_RECIPE);




                adapterForRecipelist.addItem(name,ingredient,recipe);

                list.add(name);

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
