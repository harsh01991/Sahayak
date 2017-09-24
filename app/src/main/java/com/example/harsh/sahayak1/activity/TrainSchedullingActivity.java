package com.example.harsh.sahayak1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.harsh.sahayak1.CustomAdapter.CardAdapter;
import com.example.harsh.sahayak1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TrainSchedullingActivity extends AppCompatActivity {




    TextView train_nme;    TextView train_number;
    //Creating array for day runs trains and Class Availability
    ArrayList<String> checkRunsOn;
    ArrayList<String> checkClassAvail;

    //Create a list of trainSchedule
    public List<TrainScheduleGetSet> list;

    //Creating Views
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CardAdapter adapter;


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(TrainSchedullingActivity.this,TrainSchedullingNumber.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedulling);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .penaltyLog() //Logs a message to LogCat
                .build());
        train_nme= (TextView)findViewById(R.id.trainName);
        train_number=(TextView)findViewById(R.id.trainNumber);
       // ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        checkRunsOn = new ArrayList<>();
        checkClassAvail = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our list
        list = new ArrayList<>();

        //Finally initializing our adapter
        adapter = new CardAdapter(list, TrainSchedullingActivity.this);
        recyclerView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(bundle.getString("obj"));
            JSONObject object1 = jsonObject.getJSONObject("train");
            String train_name = object1.getString("name");
            train_nme.setText(train_name);


            String train_no = object1.getString("number");
            train_number.setText(train_no);

            JSONArray jArray1 = object1.getJSONArray("classes");
            for (int i = 0; i < jArray1.length(); i++) {
                JSONObject object2 = jArray1.getJSONObject(i);
                String available = object2.getString("available");
            }

            JSONArray jArray2 = object1.getJSONArray("days");
            for (int j = 0; j < jArray2.length(); j++) {

                JSONObject object3 = jArray2.getJSONObject(j);
                String runs = object3.getString("runs");
                checkRunsOn.add(runs);
            }


            //calling method to parse json array

            JSONArray jArray3 = jsonObject.getJSONArray("route");
            for (int k = 0; k < jArray3.length(); k++) {

                TrainScheduleGetSet scheduleGetset = new TrainScheduleGetSet();
                JSONObject object4 = jArray3.getJSONObject(k);

                //Assigning Data to variable
                String departure_time = object4.getString("schdep");
                String distance = object4.getString("distance");
                String arrival_time = object4.getString("scharr");
                String fullname = object4.getString("fullname");
                int halt = object4.getInt("halt");
                int day = object4.getInt("day");

                String station_code = object4.getString("code");

                //Sending value to getter-setter class
                scheduleGetset.setHalt(halt);
                scheduleGetset.setDistance(distance);
                scheduleGetset.setCode(station_code);
                scheduleGetset.setFullname(fullname);
                scheduleGetset.setDay(day);
                scheduleGetset.setScharr(arrival_time);
                scheduleGetset.setSchdep(departure_time);
                list.add(k, scheduleGetset);

            }


            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
