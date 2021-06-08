package com.example.recrecipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private static String IP_ADDRESS = "10.0.2.2";
    private Context mContext;
    private ArrayList<ListViewItem> listItems = new ArrayList<ListViewItem>();

    public ListViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // item.xml 레이아웃을 inflate해서 참조획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // listview_item.xml 의 참조 획득
        TextView txt_id = (TextView)convertView.findViewById(R.id.txt_id);
        TextView txt_ingre = (TextView)convertView.findViewById(R.id.txt_ingre);
        TextView txt_num = (TextView)convertView.findViewById(R.id.txt_num);
        TextView txt_date = (TextView)convertView.findViewById(R.id.txt_date);

        Button btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
        Button btn_modify = (Button)convertView.findViewById(R.id.btn_modify);

        ListViewItem listItem = listItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        txt_id.setText(listItem.getIdStr());
        txt_ingre.setText(listItem.getIngreStr());
        txt_num.setText(listItem.getNumStr());
        txt_date.setText(listItem.getDateStr());

        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = listItem.getIdStr();

                DeleteData task = new DeleteData();
                task.execute("http://" + IP_ADDRESS + "/deleteIngre.php", id);

                listItems.remove(position);

                notifyDataSetChanged();
            }
        });

        //리스트 아이템 수정
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = listItem.getIdStr();

                Intent intent = new Intent(mContext.getApplicationContext(), IngredientModify.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class DeleteData extends AsyncTask<String, Void, String> {
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

            String serverURL = (String)params[0];
            String postParameters = "id=" + id;


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

    public void addItem(String id, String ingre, String num, String date){
        ListViewItem listItem = new ListViewItem();

        listItem.setIdStr(id);
        listItem.setIngreStr(ingre);
        listItem.setNumStr(num);
        listItem.setDateStr(date);

        listItems.add(listItem);
        notifyDataSetChanged();
    }
}
