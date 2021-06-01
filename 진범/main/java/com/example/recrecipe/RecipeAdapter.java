package com.example.recrecipe;
//레시피 리스트의 커스텀 리스트뷰를 위한 클래스

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<RealRecipeListItem> listViewItemList = new ArrayList<RealRecipeListItem>() ;
    Bitmap bitmap;
    // IngredientAdapter의 생성자
    public RecipeAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_inrecipe_design, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView picImageView = (ImageView) convertView.findViewById(R.id.imageView_list_recipe_explain) ;
        TextView expTextView = (TextView) convertView.findViewById(R.id.textView_list_recipe_explain) ;
        TextView tipTextView = (TextView) convertView.findViewById(R.id.textView_list_tip) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        RealRecipeListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        // 이미지 데이터 반영
        if(listViewItem.getUrl().equals("")){
            picImageView.setVisibility(View.GONE);
        }
        else {

        }


        Thread mThread = new Thread() {
            @Override
            public void run(){
                try{
                    URL url = new URL (listViewItem.getUrl());
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
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
            picImageView.setImageBitmap(bitmap);
        }catch (InterruptedException e){

        }

        // string 데이터 반영
        expTextView.setText(listViewItem.getExp());
        tipTextView.setText(listViewItem.getTip());



        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String url, String exp, String tip) {
        RealRecipeListItem item = new RealRecipeListItem();

        item.setUrl(url);
        item.setExp(exp);
        item.setTip(tip);

        listViewItemList.add(item);
    }
}