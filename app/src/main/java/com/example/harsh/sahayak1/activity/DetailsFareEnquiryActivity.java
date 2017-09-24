package com.example.harsh.sahayak1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.sahayak1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFareEnquiryActivity extends AppCompatActivity {

    @BindView(R.id.fareEnquiryTrainNo) TextView setTrainNumber;
    @BindView(R.id.fareEnquiryTrainName) TextView setTrainName;
    @BindView(R.id.fareEnquiryQuota) TextView setquota;
    @BindView(R.id.fareEnquiryFrom) TextView fromStation;
    @BindView(R.id.fareEnquiryTo) TextView toStation;
    @BindView(R.id.FareFirstAc) TextView firstAc;
    @BindView(R.id.FareSecondAc) TextView secondAc;
    @BindView(R.id.FareThirdAc) TextView thirdAc;
    @BindView(R.id.FareSleeper) TextView sleeper;
    ArrayList<String> fareDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_fare_enquiry);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        fareDetails = new ArrayList<>();



                        try {
                            JSONObject response = new JSONObject(bundle.getString("obj"));
                            if(response.getInt("response_code")==200) {
                                String stationFrom = response.getJSONObject("from").getString("name");
                                fromStation.setText(stationFrom);

                                String stationTo = response.getJSONObject("to").getString("name");
                                toStation.setText(stationTo);

                                String trainName = response.getJSONObject("train").getString("name");
                                setTrainName.setText(trainName);

                                String trainNumber = response.getJSONObject("train").getString("number");
                                setTrainNumber.setText(trainNumber);

                                String quota = response.getJSONObject("quota").getString("name");
                                setquota.setText(quota);

                                JSONArray jsonArray = response.getJSONArray("fare");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String fare = jsonArray.getJSONObject(i).getString("fare");
                                    fareDetails.add(i, fare);
                                }
                                firstAc.setText(fareDetails.get(0) + "/-");
                                secondAc.setText(fareDetails.get(1) + "/-");
                                thirdAc.setText(fareDetails.get(2) + "/-");
                                sleeper.setText(fareDetails.get(3) + "/-");

                            }else
                            {
                                Toast.makeText(DetailsFareEnquiryActivity.this,response.getString("error"),Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsFareEnquiryActivity.this,FareEnquiryActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
