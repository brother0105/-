package com.example.recrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class IngredientMenu extends AppCompatActivity {

    private String mJsonString;

    private Spinner spinner;
    private TextView tv_result;

    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<IngredientData> mArrayList;

    private EditText edt_id;
    private EditText edt_ingre;
    private EditText edt_num;
    private EditText edt_date;
    private Button btn_add;

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private static final String TAG_JSON="tester";
    private static final String TAG_ID = "id";
    private static final String TAG_INGRE = "ingre";
    private static final String TAG_NUM ="num";
    private static final String TAG_DATE ="date";

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_menu);

        // 뷰 참조
        edt_ingre = (EditText) findViewById(R.id.edt_ingre);
        edt_num = (EditText) findViewById(R.id.edt_num);
        edt_date = (EditText) findViewById(R.id.edt_date);
        btn_add = (Button) findViewById(R.id.btn_add);
        listView = (ListView) findViewById(R.id.listview);
        mArrayList = new ArrayList<>();

       // mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        adapter = new ListViewAdapter(IngredientMenu.this );
        listView.setAdapter(adapter);

        GetData task = new GetData();
        task.execute("http://10.0.2.2/getingre.php");

        // 데이터 추가하기
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adapter.addItem(edt_id.getText().toString(), edt_ingre.getText().toString(),
                                //edt_num.getText().toString(), edt_date.getText().toString());

                String ingre = edt_ingre.getText().toString();
                String num = edt_num.getText().toString();
                String date = edt_date.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insertIngre.php", ingre, num, date);

                edt_ingre.setText("");
                edt_num.setText("");
                edt_date.setText("");

                adapter.notifyDataSetChanged();

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        //드롭다운(스피너)
        spinner = (Spinner)findViewById(R.id.spinner);
        tv_result = (TextView)findViewById(R.id.tv_result);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    } //oncreate end

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(IngredientMenu.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String ingre = (String)params[1];
            String num = (String)params[2];
            String date = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "ingre=" + ingre + "&num=" + num + "&date=" + date;


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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(IngredientMenu.this,
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

                String id = item.getString(TAG_ID);
                String ingre = item.getString(TAG_INGRE);
                String num = item.getString(TAG_NUM);
                String date = item.getString(TAG_DATE);

                IngredientData ingredientData = new IngredientData();

                ingredientData.setIngreId(id);
                ingredientData.setIngreName(ingre);
                ingredientData.setIngreNum(num);
                ingredientData.setIngreDate(date);

                adapter.addItem(id, ingre, num, date);
                adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}