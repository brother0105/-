package com.example.recrecipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import java.io.File;

public class imagepopup extends Activity {

    String imageDir;

    private static String IP_ADDRESS = "10.0.2.2";
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imagepopup);
        Intent intent=getIntent();
        ImageView image = (ImageView) findViewById(R.id.imagepopup);
        imageDir=intent.getStringExtra("image");//image태그로 경로를 받음

        bitmap = BitmapFactory.decodeFile(imageDir);

        image.setImageBitmap(bitmap);

        File file = new File(imageDir);
        file.delete();//쓴 파일은 삭제




    }
}

