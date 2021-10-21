package com.example.recrecipe;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class appo_recipe_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private ArrayList<apporecipeitem> listitem = new ArrayList<apporecipeitem>();



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        View view = null;
        Context context = parent.getContext();

        switch (type){
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.appo_list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflaterchild = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflaterchild.inflate(R.layout.appo_list_child,parent,false);
                ListChildViewHolder child = new ListChildViewHolder(view);
                return child;

        }




        return null;

    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        final apporecipeitem item = listitem.get(position);
        switch(item.type){
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.listcontext);

                if(item.children==null){//펼쳐진 유무에 따른 오른쪽 이미지 변화
                    itemController.icon.setImageResource(R.drawable.menu_icon);
                }
                else{
                    itemController.icon.setImageResource(R.drawable.downarrow);
                }

                //여닫기
                itemController.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.children==null){
                            item.children = new ArrayList<apporecipeitem>();
                            int count=0;
                            int pos=listitem.indexOf(itemController.refferalItem);
                            while(listitem.size()>pos+1 && listitem.get(pos+1).type==CHILD){
                                item.children.add(listitem.remove(pos+1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos+1,count);
                            itemController.icon.setImageResource(R.drawable.downarrow);
                        } else{
                            int pos = listitem.indexOf(itemController.refferalItem);
                            int index = pos+1;
                            for(apporecipeitem i : item.children){
                                listitem.add(index,i);
                                index++;
                            }
                            notifyItemRangeInserted(pos+1,index-pos-1);
                            itemController.icon.setImageResource(R.drawable.menu_icon);
                            item.children=null;
                        }

                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder childitem = (ListChildViewHolder) holder;
                childitem.refferalItem=item;
                childitem.child_main.setText(item.listcontext);
                childitem.child_quantity.setText(item.quantity);

                //삭제 버튼
                childitem.child_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //버튼 동작 입력을 여기에 삭제 버튼 구현 필요.
                    }
                });





                break;

        }

    }



    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView icon;
        public apporecipeitem refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.appo_list_head);
            icon = (ImageView) itemView.findViewById(R.id.appo_list_head_image);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder{
        public TextView child_main;
        public TextView child_quantity;
        public Button child_button;
        public apporecipeitem refferalItem;

        public ListChildViewHolder(View itemView){
            super(itemView);
            child_main = (TextView) itemView.findViewById(R.id.child_main_text);
            child_quantity = (TextView) itemView.findViewById(R.id.child_quantity_text);
            child_button = (Button) itemView.findViewById(R.id.appo_child_button);
        }
    }



    public int getItemCount(){
        return listitem.size();
    }

    public class apporecipeitem {
        public int type;//타입구별. 0이면 header 1이면 child
        public int number;//apporecipe.java에서 childlist에서의 번호. 삭제시에 사용
        public String listcontext;//리스트에 들어갈 텍스트
        public String quantity ;//사야할 양
        public List<apporecipeitem> children;

        public apporecipeitem(){
        }

        public apporecipeitem(String a){//head일때. 본문과 type밖에 없음.
            this.listcontext=a;
            this.type=0;
        }

        public apporecipeitem(String a, String b, int c){//child일때.
            this.listcontext = a;
            this.quantity = b;
            this.type=1;
            this.number=c;
        }

    }
}

