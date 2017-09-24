package com.example.harsh.sahayak1.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.example.harsh.sahayak1.CustomAdapter.MainPageAdapter;
import com.example.harsh.sahayak1.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    Context context;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter adapter;

    RecyclerView.LayoutManager layoutManager;
    private Boolean exit = false;

    String[] text = {"Train Schedule","Train Status","Seat Availability","Fare Enquiry","Search Trains","Station Status","PNR Enquiry"};
    Integer[] icons = {R.drawable.ic_directions_subway_black_24dp,R.drawable.ic_place_black_24dp,R.drawable.ic_airline_seat_recline_normal_black_24dp,R.drawable.currency_inr1,R.drawable.ic_zoom_in_black_48dp,R.drawable.ic_layers_black_48dp,R.drawable.ic_storage_black_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
      context = MainActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainPageAdapter(context,text,icons);
        recyclerView.setAdapter(adapter);
        relativeLayout = (RelativeLayout)findViewById(R.id.activity_main);
    }

    @Override
    public void onBackPressed() {
        if(exit)
        {
            finish(); //finish Activity
        }
        else
        {
            Snackbar snackbar = Snackbar.make(relativeLayout,"Press Back Again to Exit.",Snackbar.LENGTH_SHORT);
            snackbar.show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            },3*1000);
        }
    }
}
