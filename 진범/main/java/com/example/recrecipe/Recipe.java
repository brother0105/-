package com.example.recrecipe;
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
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Recipe extends AppCompatActivity {

    public static ListView r_ingredient;
    public static IngredientAdapter ingredient_adapter;

    private ListView r_sub_ingredient;
    private IngredientAdapter sub_ingredient_adapter;

    private ListView r_sub_ingredient2;
    private IngredientAdapter sub_ingredient_adapter2;

    private ListView r_recipe;
    private RecipeAdapter recipe_adapter;
    private ScrollView scrollforrecipemain;

    private TextView invisible1;
    private TextView invisible2;

    String receivestr;
    // 발급받은 인증키
    String apiKey = "0fc288b3d2d49b8b523acee959792ea82e58b0079517e6b1940e06350089ddd1";

    // result를 선언

    RecipeData[] result = new RecipeData[20];
    RecipeIngredientData[] teda = new RecipeIngredientData[20];
    RecipeIngredientData[] teda2 = new RecipeIngredientData[20];
    RecipeIngredientData[] teda3 = new RecipeIngredientData[20];
    private int d = 0;
    private int e = 0;
    private int a = 0;
    private int b = 0;

    
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

        for (int s = 0; s < 20; s++){

            result[s] = new RecipeData();
        }
        for (int q = 0; q < 20; q++){

            teda[q] = new RecipeIngredientData();
        }
        for (int q = 0; q < 20; q++){

            teda2[q] = new RecipeIngredientData();
        }
        for (int q = 0; q < 20; q++){

            teda3[q] = new RecipeIngredientData();
        }

        scrollforrecipemain = (ScrollView) findViewById(R.id.recipescoll);

        r_recipe = (ListView)findViewById(R.id.recipeinrecipe);
        recipe_adapter = new RecipeAdapter();
        r_recipe.setAdapter(recipe_adapter);

        r_ingredient = (ListView) findViewById(R.id.recipe_ingredient);
        ingredient_adapter = new IngredientAdapter();
        r_ingredient.setAdapter(ingredient_adapter);

        r_sub_ingredient = (ListView) findViewById(R.id.sub_recipe_ingredient);
        sub_ingredient_adapter = new IngredientAdapter();
        r_sub_ingredient.setAdapter(sub_ingredient_adapter);

        r_sub_ingredient2 = (ListView) findViewById(R.id.sub_recipe_ingredient2);
        sub_ingredient_adapter2 = new IngredientAdapter();
        r_sub_ingredient2.setAdapter(sub_ingredient_adapter2);


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

        for( int k = 0; k< d; k++) {
            recipe_adapter.addItem(result[k].get_image_url(), result[k].get_discription(), result[k].get_tip());
        }

        for( int n = 0; n< e; n++) {
            ingredient_adapter.addItem(teda[n].get_name(), teda[n].get_cap());
        }

        for( int n = 0; n< a; n++) {
            sub_ingredient_adapter.addItem(teda2[n].get_name(), teda2[n].get_cap());
        }

        for( int n = 0; n< b; n++) {
            sub_ingredient_adapter2.addItem(teda3[n].get_name(), teda3[n].get_cap());
        }


        //입력이 안들어가면 보이지 않게 하기

        invisible1 = (TextView)findViewById(R.id.subingredienttext);
        invisible2 = (TextView)findViewById(R.id.sourcetext);

        if(a == 0) {
            r_sub_ingredient.setVisibility(View.GONE);
            invisible1.setVisibility(View.GONE);
        }

        if(b == 0){
            r_sub_ingredient2.setVisibility(View.GONE);
            invisible2.setVisibility(View.GONE);
        }


        r_ingredient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollforrecipemain.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        r_sub_ingredient.setOnTouchListener(new View.OnTouchListener() {
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


                    if(tycode.equals("3060001"))
                    //recipe_adapter.addItem(image_url, discription, tip);
                    teda[e].set_num(id);
                    teda[e].set_id(num);
                    teda[e].set_name(name);
                    teda[e].set_cap(cap);
                    teda[e].set_tycode(tycode);

                    e++;

                    if(tycode.equals("3060002")) {
                        teda2[a].set_num(id);
                        teda2[a].set_id(num);
                        teda2[a].set_name(name);
                        teda2[a].set_cap(cap);
                        teda2[a].set_tycode(tycode);

                        a++;

                    }
                    if(tycode.equals("3060003")) {
                        teda3[b].set_num(id);
                        teda3[b].set_id(num);
                        teda3[b].set_name(name);
                        teda3[b].set_cap(cap);
                        teda3[b].set_tycode(tycode);

                        b++;

                    }

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
        String TAG_TIP = "STEP_TIP";


        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject channel = (JSONObject) jsonObject.get(TAG_JSON);


            JSONArray jsonArray = channel.getJSONArray(TAG_JSON2);


            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap map = new HashMap<>();
                JSONObject jObject = jsonArray.getJSONObject(i);

                String id = jObject.optString(TAG_ID);
                String num = jObject.optString(TAG_NO);
                String discription = jObject.optString(TAG_DC);
                String image_url = jObject.optString(TAG_IURL);
                String tip = jObject.optString(TAG_TIP);

                if (id.equals(ide)) {


                    recipe_adapter.addItem(image_url, discription, tip);
                    result[d].set_num(id);
                    result[d].set_id(num);
                    result[d].set_discription(discription);
                    result[d].set_url(image_url);
                    result[d].set_tip(tip);

                    d++;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}