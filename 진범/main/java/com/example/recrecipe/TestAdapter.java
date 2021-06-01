package com.example.recrecipe;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.CustomViewHolder> {

    private ArrayList<TestpersonalData> mList = null;
    private Activity context = null;


    public TestAdapter(Activity context, ArrayList<TestpersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView name;
        protected TextView ingredient;
        protected TextView recipe;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.test_textView_list_id);
            this.name = (TextView) view.findViewById(R.id.test_textView_list_name);
            this.ingredient = (TextView) view.findViewById(R.id.test_textView_list_ingredient);
            this.recipe = (TextView) view.findViewById(R.id.test_textView_list_recipe);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.ingredient.setText(mList.get(position).getMember_ingredient());
        viewholder.recipe.setText(mList.get(position).getMember_recipe());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}