package com.example.harsh.sahayak1.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harsh.sahayak1.Constants.JsonKeys;
import com.example.harsh.sahayak1.Controller.AppController;
import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveTrainStatusActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    @BindView(R.id.liveTrainNo) EditText liveTrainNumber1;
    @BindView(R.id.pickedDate) EditText startDate;
    @BindView(R.id.liveTrainSbmtBtn) Button submitButton;
    int day, month, year;
    int getYear, getMonth, getDay;
    ProgressDialog progressDialog;
    String formattedMonth,formattedDayOfMonth,liveTrainNumber2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);
        ButterKnife.bind(this);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .penaltyLog() //Logs a message to LogCat
                .build());


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
            startDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(LiveTrainStatusActivity.this, LiveTrainStatusActivity.this,
                            year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();

                }
            });


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     liveTrainNumber2 = liveTrainNumber1.getText().toString();

                    if(liveTrainNumber1.length()<5)
                    {
                        liveTrainNumber1.setError("Enter 5 digits Train Number");
                    }
                    else if("".equals(liveTrainNumber1))
                        {
                            liveTrainNumber1.setError("Please Enter Train Number");
                        }

                       else  {
                            volleyCall();
                    }
                }
            });
        }
    public void volleyCall()
    {
        progressDialog = new ProgressDialog(LiveTrainStatusActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JsonKeys.LIVE_TRAIN_URL  + liveTrainNumber2 + "/date/"  + formattedDayOfMonth  +"-" +formattedMonth+"-" +year+
                        JsonKeys.apikey,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        try {
                            if(response.getInt("response_code")==200)
                            {
                                Intent intent = new Intent(LiveTrainStatusActivity.this, DetailsLiveTrainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("obj", response.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            else if(response.getInt("response_code")==204)
                            {
                                Toast.makeText(LiveTrainStatusActivity.this,"Please check the Train Number and try once again.Thankyou.",Toast.LENGTH_SHORT).show();
                            }
                            else if(response.getInt("response_code")==404)
                            {
                                Toast.makeText(LiveTrainStatusActivity.this,"Please check the input",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

                Toast.makeText(LiveTrainStatusActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

// Add a request (in this example, called stringRequest) to your RequestQueue.
        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

        @Override
        public void onDateSet (DatePicker datePicker,int i, int i1, int i2){

            getYear = i;
            getMonth = i1 + 1;
            getDay = i2;
             formattedMonth = "" + getMonth;
             formattedDayOfMonth = "" + i2;

            if(getMonth < 10){

                formattedMonth = "0" + getMonth;
            }
            if(i2 < 10){

                formattedDayOfMonth = "0" + i2;
            }
            startDate.setText(formattedDayOfMonth + "/" + formattedMonth + "/" + year);

        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LiveTrainStatusActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
