package com.example.recrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IngredientModify extends AppCompatActivity {

    private Button btn_check;

    private TextView txt_id;
    private EditText edt_ingre;
    private EditText edt_num;
    private EditText edt_date;

    private static String IP_ADDRESS = "10.0.2.2";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_modify);

        txt_id = (TextView) findViewById(R.id.txt_id);
        edt_ingre = (EditText) findViewById(R.id.edt_ingre);
        edt_num = (EditText) findViewById(R.id.edt_num);
        edt_date = (EditText) findViewById(R.id.edt_date);

        btn_check = (Button) findViewById(R.id.btn_check);

        Intent intent = getIntent();
        String receivestr = intent.getExtras().getString("id");

        txt_id.setText(receivestr);

        //확인 버튼 누를시 수정 작업을 수행
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = txt_id.getText().toString();
                String ingre = edt_ingre.getText().toString();
                String num = edt_num.getText().toString();
                String date = edt_date.getText().toString();

                ModifyData task = new ModifyData();
                task.execute("http://" + IP_ADDRESS + "/modifyIngre.php", id, ingre, num, date);

                finish();
                Intent intent = new Intent(IngredientModify.this, IngredientMenu.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }



    class ModifyData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressDialog = ProgressDialog.show(ListViewAdapter.this,
            //"Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            //mTextViewResult.setText(result);
            //Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String ingre = (String)params[2];
            String num = (String)params[3];
            String date = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&ingre=" + ingre + "&num=" + num + "&date=" + date;


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