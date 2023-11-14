package com.example.mystories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;


public class HomeFragment extends Fragment {

    private ImageView imageView;
    private Button loadBtn;
    private static DatabaseHelper db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
    }
    public void initViews(View root){
        imageView = root.findViewById(R.id.image);
        loadBtn = root.findViewById(R.id.load);
    }
    public void initEvents(View root){
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Story story = db.get();
                imageView.setImageBitmap(story.getImg());
                Toast.makeText(getContext(),story.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Activity activity = getActivity();

        View root = inflater.inflate(R.layout.home_fragment, container, false);
        initViews(root);
        initEvents(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}