package com.example.mystories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Story> dataList;
    public MyAdapter(List<Story> dataList) {
        this.dataList = dataList;
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Story data = dataList.get(position);
        holder.imageView.setImageBitmap(data.getImg());
        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContent());
        holder.time.setText(data.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public  void addItem(Story story){
        this.dataList.add(story);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, content, time;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }
}
