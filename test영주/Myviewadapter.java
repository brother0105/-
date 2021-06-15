package com.example.recrecipe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void addItem(Drawable icon, String title, String memo) {
        Myviewitem item = new Myviewitem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDate(memo);

        MyviewitemList.add(item);
    }
    public void addItem(String title, String memo) {
        Myviewitem item = new Myviewitem();

        item.setTitle(title);
        item.setDate(memo);

        MyviewitemList.add(item);
    }
}