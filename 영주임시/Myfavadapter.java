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

public class Myfavadapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    private ArrayList<Myfavitem> MyfavitemList = new ArrayList<Myfavitem>();

    // ListViewAdapter의 생성자
    public Myfavadapter() {


    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return MyfavitemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "myfavitem" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favitem, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView memoTextView = (TextView) convertView.findViewById(R.id.textView2);

        // Data Set(MyfavnviewitemList)에서 position에 위치한 데이터 참조 획득
        Myfavitem listViewItem = MyfavitemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        memoTextView.setText(listViewItem.getmemo());

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
        return MyfavitemList.get(position) ;
    }






    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String memo) {
        Myfavitem item = new Myfavitem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setMemo(memo);

        MyfavitemList.add(item);
    }
}