package com.example.harsh.sahayak1.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.harsh.sahayak1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsSeatAvailActivity extends AppCompatActivity {

    public static final  String JsonURL = "http://api.railwayapi.com/check_seat/train";
    public static final  String apikey = "apikey/ttv6xi06/";

    String fromStation="",toStation="",classCode="",quota="",upTo2Charcter="";
    ArrayList<String> date,availablity;

    @BindView(R.id.seatAvailTrainNo) TextView trainNo;
    @BindView(R.id.seatAvailTrainName) TextView trainName;
    @BindView(R.id.seatAvailFrom) TextView stationFrom;
    @BindView(R.id.seatAvailTo) TextView stationTo;
    @BindView(R.id.seatAvailClass) TextView className;
    @BindView(R.id.seatAvailQuota) TextView quotaInfo;
    @BindView(R.id.SeatAvailDate1) TextView date1;
    @BindView(R.id.SeatAvailStatus1) TextView status1;
    @BindView(R.id.SeatAvailDate2) TextView date2;
    @BindView(R.id.SeatAvailStatus2) TextView status2;
    @BindView(R.id.SeatAvailDate3) TextView date3;
    @BindView(R.id.SeatAvailStatus3) TextView status3;
    @BindView(R.id.SeatAvailDate4) TextView date4;
    @BindView(R.id.SeatAvailStatus4) TextView status4;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_seat_avail);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        date = new ArrayList<>();
        availablity = new ArrayList<>();

    Bundle bundle = getIntent().getExtras();

        try {
            JSONObject response = new JSONObject(bundle.getString("obj"));
            String trainNumber = response.getString("train_number");
            trainNo.setText(trainNumber);

            String trainNameFinal = response.getString("train_name");
            trainName.setText(trainNameFinal);

            JSONObject jsonObject1 = response.getJSONObject("from");
            String fromStation = jsonObject1.getString("name");
            stationFrom.setText(fromStation);

            JSONObject jsonObject2 = response.getJSONObject("to");
            String toStation = jsonObject2.getString("name");
            stationTo.setText(toStation);

            JSONObject jsonObject3 = response.getJSONObject("class");
            String classFullName = jsonObject3.getString("class_name");
            className.setText(classFullName);

            JSONObject jsonObject4 = response.getJSONObject("quota");
            String quota = jsonObject4.getString("quota_name");
            quotaInfo.setText(quota);

            JSONArray jsonArray = response.getJSONArray("availability");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String status = obj.getString("status");
                availablity.add(i, status);
                String statusDate = obj.getString("date");
                date.add(i, statusDate);
                Log.d("JSONArray", status + "   " + statusDate);

            }

            status1.setText(availablity.get(0));
            status2.setText(availablity.get(1));
            status3.setText(availablity.get(2));
            status4.setText(availablity.get(3));

            date1.setText(date.get(0));
            date2.setText(date.get(1));
            date3.setText(date.get(2));
            date4.setText(date.get(3));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
//        String finalday = bundle.getString("day");
//        String finalmonth = bundle.getString("month");
//        int finalYear = bundle.getInt("year");
//        String formattedMonth = "" + finalmonth;
//        fromStation = bundle.getString("fromStation");
//        toStation = bundle.getString("toStation");
//        classCode = bundle.getString("classCode");
//        quota = bundle.getString("quota");
//        upTo2Charcter = quota.substring(0,2);


//        progressDialog = new ProgressDialog(DetailsSeatAvailActivity.this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Fetching Data..");
//        progressDialog.setCancelable(true);
//        progressDialog.setCanceledOnTouchOutside(true);
//        progressDialog.show();
//        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                JsonURL + "/" + finalTrainNo + "/" + "source" + "/" + fromStation + "/" + "dest" + "/" + toStation
//                        + "/" + "date" + "/" + finalday + "-" + formattedMonth + "-" + finalYear + "/" + "class" + "/" + classCode
//                        + "/" + "quota" + "/" + upTo2Charcter + "/" + apikey,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        progressDialog.hide();
//                        try {
//                            if(response.getInt("response_code")==200) {


//                            }
//                            else
//                            {
//                                Intent intent = new Intent(DetailsSeatAvailActivity.this,SeatAvailabilityActivity.class);
//                                startActivity(intent);
//                                Toast.makeText(DetailsSeatAvailActivity.this,response.getString("error"),Toast.LENGTH_LONG).show();
//                            }
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.hide();
//                Intent intent = new Intent(DetailsSeatAvailActivity.this,SeatAvailabilityActivity.class);
//                startActivity(intent);
//                Toast.makeText(DetailsSeatAvailActivity.this,"Railway Server responding slow.Please try again after sometime.Thankyou",Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        // Add a request (in this example, called stringRequest) to your RequestQueue.
//        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
//
//        int socketTimeout = 5000; // 5 seconds. You can change it
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//        jsonObjectRequest.setRetryPolicy(policy);
 }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsSeatAvailActivity.this,SeatAvailabilityActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   }
