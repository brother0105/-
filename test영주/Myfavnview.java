package com.example.recrecipe;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//intent용
import android.content.Intent;

//이 밑으로 추가
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Myfavnview extends AppCompatActivity {

    private List<String> list;
    private SearchAdapter adapter;
    private ArrayList<String> Arraylist;
    private ListView favlist;
    private ListViewAdapter adapterForfav;
    private ListView viewlist;
    private ListViewAdapter adapterForview;
    private String mJsonString;


    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private static final String TAG_JSON="tester";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_ID = "id";
    private static final String TAG_RECNAME = "recname";
    private static final String TAG_MEMO ="memo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfavnview);

        list = new ArrayList<String>();
        Arraylist = new ArrayList<String>();
        Arraylist.addAll(list);

        adapterForfav = new ListViewAdapter();

        favlist = (ListView) findViewById(R.id.Myfav);
        favlist.setAdapter(adapterForfav);
        GetDatafav task = new GetDatafav();
        task.execute("http://"+IP_ADDRESS+"/myfav.php");

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

    private class GetDatafav extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Myfavnview.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


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

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                
                String recphoto=item.getString(TAG_PHOTO);
                String recname = item.getString(TAG_RECNAME);
                String memo = item.getString(TAG_MEMO);
                Drawable photo= ContextCompat.getDrawable(this, R.drawable.search_icon);


                Myfavitem Myfavitem = new Myfavitem();


                if(recphoto.matches("http://(.*)")){//recphoto로 받은게 url이면

                }
                else if(recphoto.matches("(.*).png")||recphoto.matches("(.*).jpg")){

                }
                //recphoto로 받은 string이 null이 거나 이미지 파일이 아니거나 url도 아닐경우 그대로 검색 아이콘
                
                Myfavitem.setIcon(photo);
                Myfavitem.setTitle(recname);
                Myfavitem.setMemo(memo);

                adapterForfav.addItem(photo, recname, memo);
                adapterForfav.notifyDataSetChanged();

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


}


