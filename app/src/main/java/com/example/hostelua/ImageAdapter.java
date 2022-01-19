package com.example.hostelua;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<ImageModel> moviesList;
    Context context1;
    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mimg;
        ImageViewHolder(View view) {
            super(view);
            mimg = view.findViewById(R.id.img_vw);

        }
    }
    public ImageAdapter(Context context, List<ImageModel> moviesList) {
        this.moviesList = moviesList;
        this.context1 = context;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.activity_gallery_item, parent, false);
        // v.setOnClickListener(mOnClickListener);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel movie = moviesList.get(position);
        holder.mimg.setImageResource(movie.getImg());
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
