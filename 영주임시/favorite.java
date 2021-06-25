package com.example.recrecipe;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//intent용
import android.content.Intent;

//이 밑으로 추가
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class favorite extends AppCompatActivity {


    private ListView favlist;
    private Myfavadapter adapterForfav;
    private String mJsonString;

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private static final String TAG_JSON="tester";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_ID = "id";//id쓰게 되면 사용될 예정
    private static final String TAG_RECNAME = "recname";
    private static final String TAG_MEMO ="memo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        adapterForfav = new Myfavadapter();

        favlist = (ListView) findViewById(R.id.Myfav);
        favlist.setAdapter(adapterForfav);
        favorite.GetData favtask = new favorite.GetData();
        favtask.execute("http://"+IP_ADDRESS+"/myfav.php");





    }


    public void onBackButtonClicked(View v) {
        Toast.makeText(getApplicationContext(), "돌아가기 버튼을 눌렀어요.", Toast.LENGTH_LONG).show();
        finish();
    }


    public void EventGotofav(View v){
        Intent intent = new Intent(getApplicationContext(), favorite.class);
        startActivity(intent);
    }

    public void EventGotomyinfo(View v){
        Intent intent = new Intent(getApplicationContext(), Myinfo.class);
        startActivity(intent);
    }

    public void titleClicked(View v){//이름 클릭시 해당 페이지로 가게

    }

    public void memoClicked(View v){//메모 클릭시 팝업창 띄워서 수정할 수 있게

    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(favorite.this,
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

                String recphoto=item.optString(TAG_PHOTO,null);
                String recname = item.getString(TAG_RECNAME);
                String memo = item.optString(TAG_MEMO,"");


                Myfavitem Myfavitem = new Myfavitem();


                Myfavitem.setTitle(recname);
                Myfavitem.setMemo(memo);

                if(recphoto==null) {
                    Drawable photo = ContextCompat.getDrawable(this, R.drawable.search_icon);
                    adapterForfav.addItem(photo, recname, memo);
                }
                else if(recphoto.matches("http://(.*)")){//recphoto로 받은게 url이면

                }
                else{//이미지 이름을 받았으면
                    Drawable photo=getResources().getDrawable(getResources().getIdentifier(recphoto,"Drawable",getPackageName()));
                    Myfavitem.setIcon(photo);
                    adapterForfav.addItem(photo, recname, memo);
                }
                //recphoto로 받은 string이 null이 거나 이미지 파일이 아니거나 url도 아닐경우 그대로 검색 아이콘

                adapterForfav.notifyDataSetChanged();

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}