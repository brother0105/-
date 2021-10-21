package com.example.recrecipe;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class apporecipe extends customtoolbar implements appo_delete {//apporecipe 화면

    private String mJsonString;

    private ArrayList<String> headlist;//head쪽 string리스트, 여기에 분류를 넣는다.
    private ArrayList<tobuylist> childlist;//child쪽 tobuylist 리스트, 이놈이 본체.

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private static final String TAG_JSON="tester";
    private static final String TAG_NUMBER = "number";//레시피 id 아마 쓸일 없음 혹시나 레시피로 바로 가게 될때 필요?
    private static final String TAG_RECNAME = "recname";//레시피 이름
    private static final String TAG_QUANTITY ="quantity";//수량
    private static final String TAG_INGRE="ingre";//재료 이름
    private static final String TAG_CATEGORY="category";//재료의 카테고리

    int viewnow;//중복클릭시 새로고침 방지용. 버튼클릭으로 새로고침시킬거면 삭제.

    EditText howmany;
    EditText whatingre;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apporecipe);

        headlist=new ArrayList<String>();

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

        recyclerView = findViewById(R.id.apporeciperecyclerview);

        howmany=(EditText) findViewById(R.id.howmany);
        whatingre=(EditText) findViewById(R.id.whatingre);

        findViewById(R.id.input_tobuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingre=whatingre.getText().toString();
                String quantity=howmany.getText().toString();

                String check1=ingre.replaceAll(" ","");
                String check2=quantity.replaceAll(" ","");

                if(check1==""||check2==""){//둘 중 하나라도 공란이 있으면
                    Toast.makeText(getApplicationContext(), "공란이 있습니다, 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else{
                    inputbuyingre task = new inputbuyingre();
                    task.execute("http://"+IP_ADDRESS + "/buyingreinput.php",ingre,quantity);

                    //////////////////////////새로고침 설정 넣어야함//////////////////////////

                }



            }
        });




        apporecipe.GetData baseinput = new apporecipe.GetData();
        baseinput.execute("http://"+IP_ADDRESS+"/buyingreload.php");

        viewnow=0;
        ViewChange(1);
    }



    private void ViewChange(int a){

        switch(a) {
            case 1:
                if(viewnow==1)
                    break;
                headlist.clear();

                for(int i=0;i<childlist.size();i++){
                    if(!headlist.contains(childlist.get(i).recipe_name))
                        headlist.add(childlist.get(i).recipe_name);//이름으로 집어넣기. 이름이 같으면 어떻게? num으로 구분하게?
                }

                appo_recipe_adapter adapter = new appo_recipe_adapter();


                viewnow=1;
                break;

            case 2:
                if(viewnow==2)
                    break;
                headlist.clear();

                for(int i=0;i<childlist.size();i++){
                    if(!headlist.contains(childlist.get(i).ingredient_name))
                        headlist.add(childlist.get(i).ingredient_name);//재료이름으로 headlist에 넣기
                }


                viewnow=2;
                break;

            default:
                break;
        }

    }

    public void on_Delete_Click (int value){

        deletebuyingre task = new deletebuyingre();
        task.execute("http://"+IP_ADDRESS+"/deletebuyingre.php",childlist.get(value).ingredient_name,childlist.get(value).category,Integer.toString(childlist.get(value).recipe_num));

        childlist.remove(value);

        /////////////////새로고침필요. 아니면 재배열
    }

    private class GetData extends AsyncTask<String, Void, String> {//php통신
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(apporecipe.this,
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
                inputData();//php로 받은 데이터를 childList에 넣는 작업
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


    private void inputData(){//php로 받은 데이터를 childList에 넣는 작업
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            childlist=new ArrayList<tobuylist>();


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                tobuylist insert = new tobuylist();
                insert.recipe_num=item.getInt(TAG_NUMBER);
                insert.category=item.getString(TAG_CATEGORY);
                insert.ingredient_name=item.getString(TAG_INGRE);
                insert.ingredient_quantity=item.getString(TAG_QUANTITY);


                if(insert.recipe_num>195453) {//mysql에 있을때
                    insert.recipe_name = item.getString(TAG_RECNAME);
                }
                else if(insert.recipe_num==0){
                    insert.recipe_name = "개인입력";
                }
                else{//api일때
                    String temp="";
                    apporecipe.Task1 getnametask = new apporecipe.Task1();
                    try{
                        temp=getnametask.execute().get();
                        insert.recipe_name = apirecipe(temp,Integer.toString(insert.recipe_num));

                    }catch(Exception e){
                        String errorString = null;

                        Log.d(TAG, "InsertData: Error ", e);
                        errorString = e.toString();
                    }
                }

                childlist.add(insert);

            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
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

    public String apirecipe(String jsonString, String a){
        String TAG_JSON = "Grid_20150827000000000226_1";
        String TAG_JSON2 = "row";
        String TAG_ID = "RECIPE_ID";
        String TAG_NAME = "RECIPE_NM_KO";

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject)jsonObject.get(TAG_JSON);
            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String name = jObject.optString(TAG_NAME);

                if(id.equals(a)){
                    return name;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return "레시피_이름정보_없음";
    }



    public class inputbuyingre extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }


        @Override
        protected String doInBackground(String... params) {

            String i_name = (String)params[1];
            String quantity = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "i_name=" + i_name + "&quantity=" + quantity;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                //Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    public class deletebuyingre extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }


        @Override
        protected String doInBackground(String... params) {

            String i_name = (String)params[1];
            String category = (String)params[2];
            String recipe_num = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "i_name=" + i_name + "&category=" + category + "&recipe_num" + recipe_num;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                //Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}
