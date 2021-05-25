package com.example.g;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Recipe extends AppCompatActivity {

    private ListView r_ingredient;
    private IngredientAdapter ingredient_adapter;

    private ListView r_recipe;
    private RecipeAdapter recipe_adapter;
    private ScrollView scrollforrecipemain;

    String receivestr;
    // 발급받은 인증키
    String apiKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";

    // result를 선언
    TestApiData result = new TestApiData();
    TestApiData teda = new TestApiData();

    
    TextView receiveView;
    TextView explain;
    ImageView frontdish;
    Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);


        Intent intent = getIntent();
        String receivestr = intent.getExtras().getString("Text");

        scrollforrecipemain = (ScrollView) findViewById(R.id.recipescoll);

        r_recipe = (ListView)findViewById(R.id.recipeinrecipe);
        recipe_adapter = new RecipeAdapter();
        r_recipe.setAdapter(recipe_adapter);

        r_ingredient = (ListView) findViewById(R.id.recipe_ingredient);
        ingredient_adapter = new IngredientAdapter();
        r_ingredient.setAdapter(ingredient_adapter);


        String resultText = "값이없음";
        String resultText2 = "값이없음";
        String resultText3 = "값이없음";

        // json 값으로 api를 통해 받아옴
        try {
            resultText = new Recipe.Task().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //api로 받아온 것을 json 파싱
        foodlistjsonParserForRecipe(resultText, receivestr);

        // json 값으로 api를 통해 받아옴
        try {
            resultText2 = new Recipe.Task2().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //api로 받아온 것을 json 파싱
        foodlistjsonParserForRecipeIngredient(resultText2, receivestr);

        // json 값으로 api를 통해 받아옴
        try {
            resultText3 = new Recipe.Task3().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //api로 받아온 것을 json 파싱
        foodlistjsonParserForRecipeInRecipe(resultText3, receivestr);

        r_ingredient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollforrecipemain.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        r_recipe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollforrecipemain.requestDisallowInterceptTouchEvent(true);
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

    public void foodlistjsonParserForRecipe(String jsonString, String ide) {

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

        // recipe 사진, 설명 처리
        frontdish = (ImageView)findViewById(R.id.dishpicture);
        explain = (TextView) findViewById(R.id.explain);
        receiveView = (TextView)findViewById(R.id.food_name);


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



                if (id.equals(ide)){

                    // 음식 이미지 처리
                    Thread mThread = new Thread() {
                        @Override
                        public void run(){
                            try{
                                URL url2 = new URL (url);
                                HttpURLConnection conn = (HttpURLConnection)url2.openConnection();
                                conn.setDoInput(true);
                                conn.connect();

                                InputStream is = conn.getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);
                            } catch (IOException ex){

                            }
                        }
                    };

                    mThread.start();

                    try{
                        mThread.join();
                        frontdish.setImageBitmap(bitmap);
                    }catch (InterruptedException e){

                    }

                    // 제목 처리
                    receiveView.setText(name);
                    // 설명 처리
                    explain.setText(sumry);

                    break;

                }

                //adapterForRecipelistforapi.addItem(url,name,sumry);

                //listforapi.add(name);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // 재료
    public class Task2 extends AsyncTask<String, Void, String> {

        String clientKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";;
        private final String ID = "########";
        String type = "/json";
        String serviceUrl = "/Grid_20150827000000000227_1";
        private String str, receiveMsg;

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://211.237.50.150:7080/openapi/"+clientKey+type+serviceUrl+"/1/219");

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

    public void foodlistjsonParserForRecipeIngredient(String jsonString, String ide) {

        String TAG_JSON = "Grid_20150827000000000227_1";
        String TAG_JSON2 = "row";
        String TAG_ID = "RECIPE_ID";
        String TAG_IRSN = "IRDNT_SN";
        String TAG_IRNM = "IRDNT_NM";
        String TAG_IRCP = "IRDNT_CPCTY";
        String TAG_IRCO= "IRDNT_TY_CODE";
        String TAG_IRTYNM = "IRDNT_TY_NM";




        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject)jsonObject.get(TAG_JSON);


            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);


            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap map = new HashMap<>();
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String num = jObject.optString(TAG_IRSN);
                String name = jObject.optString(TAG_IRNM);
                String cap = jObject.optString(TAG_IRCP);
                String tycode = jObject.optString(TAG_IRCO);
                String tyname = jObject.optString(TAG_IRTYNM);

                if (id.equals(ide)){


                    ingredient_adapter.addItem(name,cap,tyname);


                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    // 레시피
    public class Task3 extends AsyncTask<String, Void, String> {

        String clientKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";;
        private final String ID = "########";
        String type = "/json";
        String serviceUrl = "/Grid_20150827000000000228_1";
        String cir = receivestr;
        private String str, receiveMsg;

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://211.237.50.150:7080/openapi/"+clientKey+type+serviceUrl+"/1/107");

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

    public void foodlistjsonParserForRecipeInRecipe(String jsonString, String ide) {

        String TAG_JSON = "Grid_20150827000000000228_1";
        String TAG_JSON2 = "row";
        String TAG_ID = "RECIPE_ID";
        String TAG_NO = "COOKING_NO";
        String TAG_DC = "COOKING_DC";
        String TAG_IURL = "STRE_STEP_IMAGE_URL";
        String TAG_TIP= "STEP_TIP";


        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject)jsonObject.get(TAG_JSON);


            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);


            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap map = new HashMap<>();
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String num = jObject.optString(TAG_NO);
                String discription = jObject.optString(TAG_DC);
                String image_url = jObject.optString(TAG_IURL);
                String tip = jObject.optString(TAG_TIP);

                if (id.equals(ide)){

                    if(image_url.equals("")){

                        recipe_adapter.addItem(image_url, discription, tip);

                    }
                    else {

                        recipe_adapter.addItem(image_url, discription, tip);
                    }
                    recipe_adapter.notifyDataSetChanged();

                }
                recipe_adapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}