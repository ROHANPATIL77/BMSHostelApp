package com.example.hostelua;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder>{

   List<DashBoardBean> values;
    Context context1;
    DashBoardBean infodata;

    public RecyclerViewAdapter(Context context, List<DashBoardBean> list) {
        this.context1 = context;
        this.values = list;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.new_dashboard_cardui, parent, false);
        // v.setOnClickListener(mOnClickListener);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ImageViewHolder holder, int position) {
        DashBoardBean uploadCurrent = values.get(position);
        holder.textViewName.setText(uploadCurrent.getTitle());
//        Picasso.get()
//                .load(uploadCurrent.getDrawableId())
//                .placeholder(R.mipmap.ic_launcher)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);
        holder.imageView.setImageResource(uploadCurrent.getDrawableId());
        infodata=values.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            DashBoardBean dashBoardBean = values.get(position);
            String sel = dashBoardBean.getTitle();

            if(sel == "Hostel")
            {
                Intent intent = new Intent(context1, HostelActivity.class);
                context1.startActivity(intent);
            }
            else if(sel == "Rooms and Bathrooms")
            {
                Intent intent = new Intent(context1, RoomsActivity.class);
                intent.putExtra("type","Rooms and Bathrooms");
                context1.startActivity(intent);
            }
            else if (sel == "Mess") {
                Intent intent = new Intent(context1, RoomsActivity.class);
                intent.putExtra("type","Mess");
                context1.startActivity(intent);
            }
            else if( sel == "Other Facilites"){
                Intent intent = new Intent(context1, RoomsActivity.class);
                intent.putExtra("type","Other Facilites");
                context1.startActivity(intent);

            }

            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_dashboard_icon);
            textViewName = (TextView) itemView.findViewById(R.id.tv_dashboard_card);
        }
    }


}
