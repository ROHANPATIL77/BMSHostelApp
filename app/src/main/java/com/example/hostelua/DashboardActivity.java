package com.example.hostelua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
RecyclerView mRecycleV;

    Context context;
    DatabaseReference mRf;
    TextView username;

    RecyclerViewAdapter recyclerView_Adapter;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    String names[] ={"hostel","Rooms","mess","others"};
    List<DashBoardBean> list = new ArrayList<>();

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    String mDark;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mRecycleV = findViewById(R.id.recycler_view);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        username = findViewById(R.id.tv_username);

        Intent intent = getIntent();
        mDark = intent.getStringExtra("bcolor");
        if(mDark != null){
            DrawerLayout mdrawer = findViewById(R.id.drawer_layout);
            mdrawer.setBackgroundResource(R.color.teal_200);
        }

        recyclerViewLayoutManager = new GridLayoutManager(context, 2);

        initInitializer();

        mRecycleV.setLayoutManager(recyclerViewLayoutManager);
        DashBoardBean collegeNotice = new DashBoardBean();
        collegeNotice.setTitle("Hostel");
        collegeNotice.setDrawableId(R.drawable.ic_hostel);
        list.add(collegeNotice);

        DashBoardBean hostelNotice = new DashBoardBean();
        hostelNotice.setTitle("Rooms and Bathrooms");
        hostelNotice.setDrawableId(R.drawable.ic_new_rooms);
        list.add(hostelNotice);

        DashBoardBean complaintActivity = new DashBoardBean();
        complaintActivity.setTitle("Mess");
        complaintActivity.setDrawableId(R.drawable.ic_mess);
        list.add(complaintActivity);

        DashBoardBean complaintListActivity = new DashBoardBean();
        complaintListActivity.setTitle("Other Facilites");
        complaintListActivity.setDrawableId(R.drawable.ic_other_facilities);
        list.add(complaintListActivity);


        recyclerView_Adapter = new RecyclerViewAdapter(DashboardActivity.this,list);

        mRecycleV.setAdapter(recyclerView_Adapter);

        mRf= FirebaseDatabase.getInstance().getReference("personaldata").child(cleanEmail(mUser.getEmail()));
        mRf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               username.setText( dataSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }
    private String cleanEmail(String originalEmail) {
        return originalEmail.replaceAll("\\.", ",");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
//                Toast.makeText(DashboardActivity.this,"at",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Intent intent1 = new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(intent1);
                finish();
                break;

        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initInitializer () {
        int initValue = 0;


        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }

    private void addDrawerItems() {
         ArrayAdapter<String> mAdapter;
        final String[] navArray = {"About","App Version", "My Account", "Settings" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String optionSelected = navArray[position];
                if (optionSelected == "My Account") {
                    Intent intent=new Intent(DashboardActivity.this, AccntActivity.class);
//                    String getmail=currentUser.getEmail();
//                    intent.putExtra("Email",getmail);
                    startActivity(intent);
                } else if (optionSelected == "Settings") {
                    Intent intent = new Intent(DashboardActivity.this,SettingsActivity.class);
                    startActivity(intent);

                } else if(optionSelected == "About"){
                      Intent intent = new Intent(DashboardActivity.this, AbtActivity.class);
                      startActivity(intent);
                }
                else if(optionSelected == "App Version"){
                    Intent intent = new Intent(DashboardActivity.this, AppActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}