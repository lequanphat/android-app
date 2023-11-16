package com.example.mystories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Story> dataList;
    private static DatabaseHelper db;
    public MyAdapter(Context context, List<Story> dataList) {
        this.dataList = dataList;
        db = new DatabaseHelper(context);
    }

    public void addData(Story story){
        dataList.add(0, story);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Story data = dataList.get(position);
        holder.imageView.setImageBitmap(data.getImg());
        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContent());
        holder.time.setText(formatStoryTime(data.getCreated_at()));


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.showConfirm(v.getContext(), "Bạn muốn xóa story "+data.getTitle()+"?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteStory(data.getCreated_at());
                        dataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public  void addItem(Story story){
        this.dataList.add(story);
        notifyDataSetChanged();
    }
    public String formatStoryTime(String created_at) {
        LocalDateTime currentTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            LocalDateTime createdAt = LocalDateTime.parse(created_at, formatter);
            Duration duration = Duration.between(createdAt, currentTime);
            long seconds = duration.getSeconds();

            if (seconds < 60) {
                return seconds + " giây trước";
            } else if (seconds < 3600) {
                long minutes = seconds / 60;
                return minutes + " phút trước";
            } else if (seconds < 86400) {
                long hours = seconds / 3600;
                return hours + " giờ trước";
            }
        }
        return created_at;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, content, time;
        public ImageButton deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            itemView.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), title.getText().toString(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
