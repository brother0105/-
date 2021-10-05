package com.example.recrecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class appo_sort_recipe extends Fragment {//apporecipe 내부의 recipe 정렬 fragment

    private RecyclerView recyclerView;
//    private 어댑터자리


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup fragmentView = (ViewGroup)inflater.inflate(R.layout.appo_sort_recipe,container,false);

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recipe_recycle);



        return fragmentView;
    }

}
