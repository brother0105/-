package com.example.g;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestApi extends AppCompatActivity {

    private List<String> listforapi;        // 데이터를 넣은 리스트 변수
    private ListView listView;              // 검색을 보여줄 리스트 변수
    private EditText editSearch;            // 검색어를 입력할 input 창
    private SearchAdapter adapter;          // 리스트뷰에 연결할 adapter
    private ArrayList<String> Arraylistforapi;
    private ScrollView p2;
    private int t;                          // 전송할때 어떤 것 전송할지 알려주는 int

    // 발급받은 인증키
    String apiKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";

    TestApiData[] result = new TestApiData[20];
    TestApiData teda = new TestApiData();
    private int d = 0;

    //리스트 추가
    private ListView recipelist;
    private CopyListViewAdapter_url adapterForRecipelistforapi;
    // 종료



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_test);

        //scroll
        p2 = (ScrollView) findViewById(R.id.mainviewforapi);

        editSearch = (EditText) findViewById(R.id.editSearchforapi);
        listView = (ListView) findViewById(R.id.listViewforapi);

        for (int s = 0; s < 20; s++){

            result[s] = new TestApiData();
        }

        //리스트 추가
        adapterForRecipelistforapi = new CopyListViewAdapter_url();
        recipelist = (ListView) findViewById(R.id.testforapi);
        recipelist.setAdapter(adapterForRecipelistforapi);
        // 종료

        d =0;

        String resultText = "값이없음";

        try {
            resultText = new Task().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        //리스트를 생성
        listforapi = new ArrayList<String>();

        foodlistjsonParser(resultText);
        //검색에 사용할 데이터를 미리 저장
        settingList();

        //리스트의 모든 데이터를 arraylist에 복사한다.
        //list 복사본을 만든다.

        Arraylistforapi = new ArrayList<String>();
        Arraylistforapi.addAll(listforapi);

        //리스트에 연동될 adapter 생성
        adapter = new SearchAdapter(listforapi, this);

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

                String data = listforapi.get(position);
                editSearch.setText(data);
            }
        });

        recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sen = adapterForRecipelistforapi.gotointent(adapterForRecipelistforapi.getItemId(position));

                for(int k = 0; k < 20; k++){
                    if(result[k].get_name() == sen){
                        t = k;
                    }
                }

                Intent intent = new Intent(TestApi.this, Recipe.class);
                intent.putExtra("Text",result[t].get_id());
                startActivity(intent);

            }
        });



        listforapi.clear();

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                p2.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        recipelist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                p2.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }

    public class Task extends AsyncTask<String, Void, String> {

        String clientKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";;
        private final String ID = "########";
        String type = "/json";
        String serviceUrl = "/Grid_20150827000000000226_1";
        private String str, receiveMsg;

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://211.237.50.150:7080/openapi/"+clientKey+type+serviceUrl+"/1/20");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", receiveMsg);

                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }

    }

    public void foodlistjsonParser(String jsonString) {

        String TAG_JSON = "Grid_20150827000000000226_1";
        String TAG_JSON2 = "row";
        String TAG_ID = "RECIPE_ID";
        String TAG_NAME = "RECIPE_NM_KO";
        String TAG_SUMRY = "SUMRY";
        String TAG_TY = "TY_NM";
        String TAG_TIME = "COOKING_TIME";
        String TAG_CAL = "CALORIE";
        String TAG_LEVEL = "LEVEL_NM";
        String TAG_URL = "IMG_URL";


        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject)jsonObject.get(TAG_JSON);


            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);


            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap map = new HashMap<>();
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String name = jObject.optString(TAG_NAME);
                String sumry = jObject.optString(TAG_SUMRY);
                String type = jObject.optString(TAG_TY);
                String time = jObject.optString(TAG_TIME);
                String cal = jObject.optString(TAG_CAL);
                String level = jObject.optString(TAG_LEVEL);
                String url = jObject.optString(TAG_URL);

                result[d].set_id(id);
                result[d].set_name(name);
                result[d].set_summery(sumry);
                result[d].set_type(type);
                result[d].set_cooking_time(time);
                result[d].set_calorie(cal);
                result[d].set_level(level);
                result[d].set_url(url);

                d++;

                adapterForRecipelistforapi.addItem(url,name,sumry);

                listforapi.add(name);

                

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //검색을 수행하는 메소드

    public void search(String charText) {

        //문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        listforapi.clear();

        //문자 입력이 없을 때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            //검색 하지 않을 때 리스트 뷰가 보이지 않게 한다.
            listView.setVisibility(View.GONE);
            //list.addAll(Arraylist);
        }
        //문자를 입력할때
        else {

            //리스트의 모든 데이터를 검색
            for (int i = 0; i < Arraylistforapi.size(); i++)
            {
                //arraylist의 모든 데이터에 입력받은 단어(charTxt)가 포함되어 있으면 true를 반환
                if(Arraylistforapi.get(i).toLowerCase().contains(charText))
                {
                    //검색된 데이터를 리스트에 추가
                    listforapi.add(Arraylistforapi.get(i));
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
        EditText editText = (EditText)findViewById(R.id.editSearchforapi);
        String Text = editText.getText().toString();
        for(int k = 0; k < 20; k++){
            if(result[k].get_name() == Text){
                    t = k;
            }
        }

        Intent intent = new Intent(TestApi.this, Recipe.class);
        intent.putExtra("Text",result[t].get_id());
        startActivity(intent);

    }

    private void settingList(){
        listforapi.add("kimchi");
        listforapi.add("soup");
        listforapi.add("ramen");
        listforapi.add("비빔밥");
        listforapi.add("sandwich");
        listforapi.add("볶음밥");
        listforapi.add("고추장찌개");
        listforapi.add("salad");
        listforapi.add("피자");
        listforapi.add("탕수육");
        listforapi.add("꽁치찌개");
        listforapi.add("간장밥");
        listforapi.add("고등어조림");
        listforapi.add("갈치구이");
        listforapi.add("매운해물탕");
    }


}