package com.example.recrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends customtoolbar {

    Bitmap bitmap;
    AlgorithmData[] imageset = new AlgorithmData[7];

    String[] recentrecipe = new String[5];

    String a;
    int check = 0;
    int cForRecent = 0;
    private String mJsonString;
    private TextView ta;
    private TextView ta2;

    public static String testword1;
    String testword2;

    private static String IP_ADDRESS = "192.168.219.136";
    private static String TAG = "phpForMain";

    Thread mThread = new Thread();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int t = 0; t < 5; t++){
            recentrecipe[t] = new String();
        }
        for (int t = 0; t < 5; t++){
            imageset[t] = new AlgorithmData();
        }

        String resultText5 = "값이없음";
        String resultText6 = "값이없음";

        ImageView mainimage = (ImageView)findViewById(R.id.imageViewMain);

        //db에서 최근 본 레시피, 즐겨찾기 가져오기
      //  GetData task = new GetData();
     //   task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");


        // json 값으로 database에서 data를 받아옴
        try {
            resultText6 = new MainActivity.GetData().execute("http://" + IP_ADDRESS + "/getjson.php", "").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        showResult(resultText6);

        // json 값으로 api를 통해 받아옴
        try {
            resultText5 = new MainActivity.Task1().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //api로 받아온 것을 json 파싱
        setimage(resultText5, "초보환영");

        Random random =new Random();

        int a = random.nextInt(3);

        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                // 원래 하려던 동작 (UI변경 작업 등)
                mainimage.setImageBitmap(bitmap);
            }
        };

        Thread mThread = new Thread()  {
            @Override
            public void run() {
                while (true) {
                    try{
                        int a = random.nextInt(3);

                        URL url2 = new URL (imageset[a].get_url());
                        HttpURLConnection conn = (HttpURLConnection)url2.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                    } catch (IOException ex){
                    }
                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace(); }
                }
            }
        };
        mThread.start();

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

    public void setimage(String jsonString, String ide) {

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


                if (level.equals(ide)){

                    if(check<7){

                        for(int s = 0; s <= cForRecent; s++){

                            if(recentrecipe[s].equals(name)){
                                imageset[check].set_url(url);
                                imageset[check].set_id(id);
                                check++;
                            }
                        }

                    }
                    else{

                        break;

                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class GetData extends AsyncTask<String, Void, String>{

        String errorString = null;


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

    public void showResult(String get){

        String TAG_JSON="webnautes";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_COUNTRY ="country";

        try {
            JSONObject jsonObject = new JSONObject(get);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String country = item.getString(TAG_COUNTRY);

                recentrecipe[cForRecent] = name;
                cForRecent++;
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }





    private class In_Main_GET_Ingredient_Data extends AsyncTask<String, Void, String>{

        String errorString = null;


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

    public void In_Main_Ingredient_Result(String get){

        String TAG_JSON="webnautes";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_COUNTRY ="country";

        try {
            JSONObject jsonObject = new JSONObject(get);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String country = item.getString(TAG_COUNTRY);

                recentrecipe[cForRecent] = name;
                cForRecent++;
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }



    public void onButtonClick(View v) {
        Intent intent = new Intent(this, TestR.class);
        startActivity(intent);
    }
    public void onButtonClick2(View v) {
        Intent intent = new Intent(this, TestSetInterval.class);
        startActivity(intent);
    }

    public void onButtonClick3(View v) {
        Intent intent = new Intent(this, Testinternet.class);
        startActivity(intent);
    }

    public void onButtonClick4(View v) {
        Intent intent = new Intent(this, TestreceivefromInternet.class);
        startActivity(intent);
    }

    public void onButtonClick5(View v) {
        Intent intent = new Intent(this, CopyRecipelist.class);
        startActivity(intent);
    }

    public void onButtonClick6(View v) {
        Intent intent = new Intent(this, TestApi.class);
        startActivity(intent);
    }

    public void onButtonClick7(View v) {
        Intent intent = new Intent(this, TestUpdate.class);
        startActivity(intent);
    }

    public void EventGotofav(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
