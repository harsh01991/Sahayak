package com.example.harsh.sahayak1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsLiveTrainActivity extends AppCompatActivity {



    @BindView(R.id.liveTrainNumber) TextView getliveTrainNumber;
    @BindView(R.id.liveStationName) TextView getLiveStationName;
    @BindView(R.id.liveTrainDate) TextView getLiveTrainDate;
    @BindView(R.id.liveTrainSchhArr) TextView getliveTrainSchhArr;
    @BindView(R.id.liveTrainExpArr) TextView getliveTrainExpArr;
    @BindView(R.id.liveTrainStatus) TextView getliveTrainStatus;
    @BindView(R.id.liveTrainSchDep) TextView getliveTrainSchDep;
    @BindView(R.id.liveTrainExpDep) TextView getliveTrainExpDep;
    @BindView(R.id.livePosition) TextView getlivePosition;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsLiveTrainActivity.this,LiveTrainStatusActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatils_live_train);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .penaltyLog() //Logs a message to LogCat
                .build());

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();


        try {
            JSONObject response = new JSONObject(bundle.getString("obj"));
            if (response.getInt("response_code") == 200) {
                String trainNumber = response.getString("train_number");
                getliveTrainNumber.setText(trainNumber);

                String position = response.getString("position");
                getlivePosition.setText(position);

                JSONObject currStation = response.getJSONObject("current_station");

                String schArr = currStation.getString("scharr");
                getliveTrainSchhArr.setText(schArr);

                JSONObject stationName = currStation.getJSONObject("station_");

                String stnFullname = stationName.getString("name");
                getLiveStationName.setText(stnFullname);


                String actArr = currStation.getString("actarr");
                getliveTrainExpArr.setText(actArr);

                String schArrDate = currStation.getString("scharr_date");
                getLiveTrainDate.setText(schArrDate);

                String actDep = currStation.getString("actdep");
                getliveTrainExpDep.setText(actDep);
                String status = currStation.getString("status");
                getliveTrainStatus.setText(status);
                String schDep = currStation.getString("schdep");
                getliveTrainSchDep.setText(schDep);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
