package com.example.recrecipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.ArrayList;

public class Myviewadapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    private ArrayList<Myviewitem> MyviewitemList = new ArrayList<Myviewitem>();

    // ListViewAdapter의 생성자
    public Myviewadapter() {


    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return MyviewitemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "viewitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.viewitem, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.recipetitle);
        TextView dateTextView= (TextView) convertView.findViewById(R.id.favdate);

        // Data Set(MyfavnviewitemList)에서 position에 위치한 데이터 참조 획득
        Myviewitem listViewItem = MyviewitemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        dateTextView.setText(listViewItem.getdate());


        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), imagepopup.class);

                String randnum = (int)(Math.random()*10000000)+"";//랜덤한 숫자.jpg 생성

                File files = new File(context.getCacheDir()+"/"+randnum+".jpg");
                //해당 번호의 파일이 있는지 확인

                while(files.exists()==true){
                    randnum = (int)(Math.random()*10000000)+"";//랜덤한 숫자.jpg 생성
                    files = new File(context.getCacheDir()+"/"+randnum+".jpg");
                }

                try{
                    files.createNewFile();
                    FileOutputStream out = new FileOutputStream(files);//outputstream 생성

                    //이미 있는 item의 drawable을 bitmap으로 변환
                    Bitmap bitmap = ((BitmapDrawable)listViewItem.getIcon()).getBitmap();
                    //변환한 bitmap을 압축한 뒤, outputstream으로 보내어 저장함
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

                    out.close();//사용한 outputstream을 닫음


                }catch(Exception e){
                    Log.e("fileerror","file error in favadapter, "+e.getMessage());

                }

                intent.putExtra("image",context.getCacheDir()+"/"+randnum+".jpg");//image태그로 경로를 넘김
                context.startActivity(intent);
            }

        });
        titleTextView.setOnClickListener(new View.OnClickListener() {//해당 레시피로 가는 거
            @Override
            public void onClick(View v) {//api와 mysql내의 레시피를 구분하기 위한 if문 필요


                String Textincustom = (String) listViewItem.getnumber();
                Intent intent = new Intent(context.getApplicationContext(), Recipe.class);
                intent.putExtra("Text",Textincustom);
                context.startActivity(intent);

            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//myview는 favorite과 다르게 날짜를 클릭해도 레시피로 가게
                String Textincustom = (String) listViewItem.getnumber();
                Intent intent = new Intent(context.getApplicationContext(), Recipe.class);
                intent.putExtra("Text",Textincustom);
                context.startActivity(intent);
            }
        });


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
        return MyviewitemList.get(position) ;
    }






    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String date, String number) {
        Myviewitem item = new Myviewitem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDate(date);
        item.setnum(number);

        MyviewitemList.add(item);
    }
}