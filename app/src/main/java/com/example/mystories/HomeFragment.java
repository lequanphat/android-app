package com.example.mystories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private static DatabaseHelper db;
    private MyViewModel myViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }
    public void initViews(View root){
        recyclerView = root.findViewById(R.id.recyclerView);
    }

    public void setUpRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
    }
    public void loadData(){
        List<Story> dataList = db.getAllStories();
        MyAdapter adapter = new MyAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.home_fragment, container, false);
        initViews(root);

        setUpRecycler();

        myViewModel.getData().observe(getViewLifecycleOwner(), newStory -> {
            ((MyAdapter)recyclerView.getAdapter()).addData(newStory);
        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}