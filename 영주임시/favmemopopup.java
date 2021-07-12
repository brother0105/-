package com.example.recrecipe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class favmemopopup extends Activity {

    TextView memotitle;
    EditText memoedit;


    private static String IP_ADDRESS = "10.0.2.2";



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String title;
        String memo;
        String number;


        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.favmemopopup);

        memotitle = (TextView)findViewById(R.id.memotitle);
        memoedit = (EditText)findViewById(R.id.memoedit);

        title=intent.getStringExtra("name");
        memo=intent.getStringExtra("memo");
        number=intent.getStringExtra("number");

        memotitle.setText("메모 수정 : " + title);
        memoedit.setText(memo);

        Button modify = (Button) findViewById(R.id.memomodify);
        Button cancle = (Button) findViewById(R.id.memocancle);

        modify.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                String editdata = memoedit.getText().toString();

                ModifyMemo task = new ModifyMemo();
                task.execute("http://"+IP_ADDRESS + "/favmemo.php",number,editdata);

                setResult(RESULT_OK);
                finish();


            }

        });

        cancle.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }

        });

    }
}


class ModifyMemo extends AsyncTask<String, Void, String> {
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
        String memo = (String)params[2];

        String serverURL = (String)params[0];
        String postParameters = "id=" + id + "&memo=" + memo;


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
