package com.example.recrecipe;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class myview extends customtoolbar {


    private ListView viewlist;
    private Myviewadapter adapterForview;
    private String mJsonString;

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private static final String TAG_JSON="tester";
    private static final String TAG_PHOTO = "recphoto";
    private static final String TAG_ID = "id";//id쓰게 되면 사용될 예정
    private static final String TAG_RECNAME = "recname";
    private static final String TAG_DATE ="date";
    private static final String TAG_NUM="number";
    private static String temp="";
    Drawable photo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview);



        adapterForview = new Myviewadapter();

        viewlist = (ListView) findViewById(R.id.Myview);
        viewlist.setAdapter(adapterForview);

        Task1 viewtask2 = new Task1();
        try {
            temp=viewtask2.execute().get();
        }catch (Exception e){
            String errorString = null;

            Log.d(TAG, "InsertData: Error ", e);
            errorString = e.toString();

        }
        GetData favtask = new GetData();
        favtask.execute("http://"+IP_ADDRESS+"/myview.php");

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterForview.notifyDataSetChanged();
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

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(myview.this,
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


    public class Task1 extends AsyncTask<String, Void, String> {

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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                view_data view = new view_data();

                view.setname(item.optString(TAG_RECNAME,""));
                view.setdate(item.getString(TAG_DATE));
                view.setphoto(item.optString(TAG_PHOTO,null));
                view.setnumber(item.getString(TAG_NUM));


                Myviewitem Myviewitem = new Myviewitem();


                //레시피가 api쪽이면 이름과 사진을 여기서 받아옴
                if(Integer.parseInt(view.getnumber())<=195453){//api쪽일때,

                    apirecipe(temp,view);

                }



                Myviewitem.setTitle(view.getname());
                Myviewitem.setDate(view.getdate());

                if(view.getphoto().matches("https://(.*)")||view.getphoto().matches("http://(.*)")){//recphoto로 받은게 url이면

                    Thread mThread = new Thread() {
                        @Override
                        public void run(){
                            try{
                                URL url2 = new URL (view.getphoto());
                                HttpURLConnection conn = (HttpURLConnection)url2.openConnection();
                                conn.setDoInput(true);
                                conn.connect();

                                InputStream is = conn.getInputStream();
                                photo = new BitmapDrawable(is);
                            } catch (Exception ex){
                                Log.d(TAG, "GetData : Error ", ex);
                                String errorString = ex.toString();

                            }
                        }
                    };
                    mThread.start();
                    try{
                        mThread.join();
                        adapterForview.addItem(photo, view.getname(), view.getdate(),view.getnumber());
                    }catch(Exception e){
                        photo = ContextCompat.getDrawable(this, R.drawable.search_icon);
                        adapterForview.addItem(photo, view.getname(), view.getdate(),view.getnumber());
                    }

                }
                else{//이미지 이름을 받았으면

                    try {
                        /*
                        int photoid = getResources().getIdentifier(view.getphoto(), "drawable", getPackageName());
                        photo = getResources().getDrawable(photoid);
                        Myviewitem.setIcon(photo);
                        adapterForview.addItem(photo, view.getname(), view.getdate(),view.getnumber());
                        */
                        File storageDir = new File(getFilesDir() + "/capture");
                        String filename = view.getphoto();

                        File file = new File(storageDir, filename);
                        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                        photo = new BitmapDrawable(bitmap);
                        adapterForview.addItem(photo, view.getname(), view.getdate(),view.getnumber());
                    } catch(Exception ex){
                        photo = ContextCompat.getDrawable(this, R.drawable.search_icon);
                        adapterForview.addItem(photo, view.getname(), view.getdate(),view.getnumber());
                    }

                }
                //recphoto로 받은 string이 null이 거나 이미지 파일이 아니거나 url도 아닐경우 그대로 검색 아이콘

                adapterForview.notifyDataSetChanged();

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    public void apirecipe(String jsonString, view_data fav){
        String TAG_JSON = "Grid_20150827000000000226_1";
        String TAG_JSON2 = "row";
        String TAG_ID = "RECIPE_ID";
        String TAG_NAME = "RECIPE_NM_KO";
        String TAG_URL = "IMG_URL";

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject)jsonObject.get(TAG_JSON);
            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);

            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap map = new HashMap<>();
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String name = jObject.optString(TAG_NAME);
                String url = jObject.optString(TAG_URL);

                if(id.equals(fav.getnumber())){
                    fav.setname(name);
                    fav.setphoto(url);
                    break;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
class view_data{
    String recname=null;
    String recphoto=null;
    String date=null;
    String number=null;

    public void setname(String a){
        recname=a;
    }
    public void setphoto(String a){
        recphoto=a;
    }
    public void setdate(String a){
        date=a;
    }
    public void setnumber(String a){
        number=a;
    }

    public String getname(){
        return recname;
    }

    public String getphoto(){
        return recphoto;
    }
    public String getdate(){
        return date;
    }
    public String getnumber(){
        return number;
    }

}