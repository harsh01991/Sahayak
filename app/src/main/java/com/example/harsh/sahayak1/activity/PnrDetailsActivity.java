package com.example.harsh.sahayak1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PnrDetailsActivity extends AppCompatActivity {

    @BindView(R.id.trainName2) TextView trainName;
    @BindView(R.id.trainNumber) TextView trainNumber;
    @BindView(R.id.pnrNumber) TextView pnrNumber;
    @BindView(R.id.dateOfJourney) TextView dateOfJourney;
    @BindView(R.id.chartPrepared) TextView chartPrepared;
    @BindView(R.id.className) TextView className;
    @BindView(R.id.totalPassenger) TextView totalPassenger;
    @BindView(R.id.trainStartDate) TextView trainStartDate;
    @BindView(R.id.stationFrom) TextView stationFrom;
    @BindView(R.id.boardingPoint) TextView boardingPoint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();


        try {
            JSONObject response = new JSONObject(bundle.getString("obj"));
            if(response.getInt("response_code")==200) {
                trainName.setText(response.getString("train_name"));
                trainNumber.setText(response.getString("train_num"));
                pnrNumber.setText(response.getString("pnr"));
                dateOfJourney.setText(response.getString("doj"));
                chartPrepared.setText(response.getString("chart_prepared"));
                className.setText(response.getString("class"));
                totalPassenger.setText(response.getString("total_passengers"));
                stationFrom.setText(response.getJSONObject("from_station").getString("name"));
                boardingPoint.setText(response.getJSONObject("boarding_point").getString("name"));


            }else
        {
            Toast.makeText(PnrDetailsActivity.this,response.getString("error"),Toast.LENGTH_LONG).show();
        }

    }catch (JSONException e)
    {
        e.printStackTrace();
    }

}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PnrDetailsActivity.this,PnrNoActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }

