package com.example.hostelua;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {
String data;
int[] mImgIds;
    RecyclerView recyclerView;
    private List<ImageModel> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Intent intent = getIntent();
        data = intent.getStringExtra("type");

Log.v("type","data"+data);
//        setupToolbar("Gallery");
        enableNavigation();

        recyclerView = findViewById(R.id.recycleview);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);


        TextView heading = findViewById(R.id.tv_headline);

        TextView textView = findViewById(R.id.tv_head_desc);
//
        if (data.equals("Rooms and Bathrooms")) {
            heading.setText(data);
            textView.setText("All Hostels are exclusively for both boys and girls & rooms are all kind of single, double & triple sharing with toilet and bathrooms. Each student is provided with a wardrobe, study table, chair, cot and book storing cabinet. The building is equipped with solar water heating System of 3,000 LPD capacity. The facilities include project room, 24/7 Wi-fi-Internet accessibility, TV, Gymnasium, Washing Machine, power back-up, RO drinking water system, Indoor Sports facilities and parking space for student vehicles. All hostels are strictly under CC TV surveillance.");
            ImageModel img = new ImageModel();
            img.setImg(R.drawable.hostela);

            ImageModel im2 = new ImageModel();
            im2.setImg(R.drawable.hostelb);
            ImageModel im3 = new ImageModel();
            im3.setImg(R.drawable.hostelc);
            ImageModel im4 = new ImageModel();
            im4.setImg(R.drawable.hosteld);
            ImageModel im5 = new ImageModel();
            im5.setImg(R.drawable.hostele);

            movieList.add(im2);
            movieList.add(img);
            movieList.add(im3);
            movieList.add(im4);
            movieList.add(im5);

             ImageAdapter imageAdapter = new ImageAdapter(RoomsActivity.this,movieList);
             recyclerView.setAdapter(imageAdapter);

        }
        else if (data.equals("Mess")) {
            heading.setText(data);
            textView.setText("The mess has a centralized modern kitchen and a separate dining hall for boys and girls, which accommodates 180 boys & 60 girls at a time. This has both vegetarian and non-vegetarian centralized modern kitchens. Stores for provisions, vegetables and other commodities are placed in the basement. General menus for each month is are decided by the hostelite representatives selected by the hostels. Special menus are introduced and served on all important festivals.");
            ImageModel img = new ImageModel();
            img.setImg(R.drawable.messa);

            ImageModel im2 = new ImageModel();
            im2.setImg(R.drawable.messb);
            ImageModel im3 = new ImageModel();
            im3.setImg(R.drawable.messc);
            ImageModel im4 = new ImageModel();
            im4.setImg(R.drawable.messd);
            ImageModel im5 = new ImageModel();
            im5.setImg(R.drawable.messe);

            ImageModel im6= new ImageModel();
            im6.setImg(R.drawable.messf);

            movieList.add(im2);
            movieList.add(img);
            movieList.add(im3);
            movieList.add(im4);
            movieList.add(im5);
            movieList.add(im6);

            ImageAdapter imageAdapter = new ImageAdapter(RoomsActivity.this,movieList);
            recyclerView.setAdapter(imageAdapter);

        } else if (data .equals("Other Facilites")) {
            heading.setText(data);
            textView.setText("The other facilities include water heaters with a capacity of 3000 liters per day, TV lounge, Vending Machine, Washing machine, Volley Ball Court, Indoor games like Carom Board, Table Tennis and a Multi Gym are provided to the students for their convenience");

            ImageModel img = new ImageModel();
            img.setImg(R.drawable.otera);
            ImageModel im2 = new ImageModel();
            im2.setImg(R.drawable.otherb);
            ImageModel im3 = new ImageModel();
            im3.setImg(R.drawable.otherc);
            ImageModel im4 = new ImageModel();
            im4.setImg(R.drawable.otherd);
            ImageModel im5 = new ImageModel();
            im5.setImg(R.drawable.othere);
            ImageModel im6= new ImageModel();
            im6.setImg(R.drawable.otherf);
            ImageModel im7= new ImageModel();
            im7.setImg(R.drawable.otherg);
            ImageModel im8= new ImageModel();
            im8.setImg(R.drawable.otherh);

            movieList.add(im2);
            movieList.add(img);
            movieList.add(im3);
            movieList.add(im4);
            movieList.add(im5);
            movieList.add(im6);
            movieList.add(im7);
            movieList.add(im8);

            ImageAdapter imageAdapter = new ImageAdapter(RoomsActivity.this,movieList);
            recyclerView.setAdapter(imageAdapter);


        }

    }
    public void enableNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}