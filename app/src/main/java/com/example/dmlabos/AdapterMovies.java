package com.example.dmlabos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MyViewHolder> {

    ArrayList<String> title;
    ArrayList<String> posterPath;
    Context context;

    public AdapterMovies(Context ct, ArrayList<String> titles, ArrayList<String> path){
        context=ct;
        title=titles;
        posterPath=path;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rowTitle.setText(title.get(position));
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+posterPath.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView rowTitle;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rowTitle=itemView.findViewById(R.id.rowTitleTextView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
