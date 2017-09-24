package com.example.harsh.sahayak1.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harsh.sahayak1.Constants.JsonKeys;
import com.example.harsh.sahayak1.Controller.AppController;
import com.example.harsh.sahayak1.R;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FareEnquiryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.activity_seat_availability) RelativeLayout relativeLayout;
    @BindView(R.id.seatAvailTrainNo) EditText trainNumber;
    @BindView(R.id.seatAvailFrom) AutoCompleteTextView fromStation;
    @BindView(R.id.seatAvailTo) AutoCompleteTextView toStation;
    @BindView(R.id.pickedDate) EditText date;
    @BindView(R.id.seatAvailSbmtBtn) Button submitButton;
    @BindView(R.id.quotaSpinner)  Spinner spinner1;


    int day, month, year;
    int getYear, getMonth, getDay;
    int intTrainNumber;

    ProgressDialog progressDialog;
    String getTrainNumber = "", getFromStation = "", getToStation = "", getDate = "",  getQuota = "",first = "",second = "",upTo2Charcter;
    String formattedMonth,formattedDayOfMonth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FareEnquiryActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_enquiry);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Snackbar snackbar = Snackbar.make(relativeLayout, "Please Fill All Details", Snackbar.LENGTH_SHORT);
        snackbar.show();

        Resources resources = getResources();
        String[] stationCodes = resources.getStringArray(R.array.stationCodeList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, stationCodes);
        fromStation.setThreshold(1);
        toStation.setThreshold(1);
        fromStation.setAdapter(arrayAdapter);
        toStation.setAdapter(arrayAdapter);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(FareEnquiryActivity.this, FareEnquiryActivity.this,
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        String[] quotaList = resources.getStringArray(R.array.quota_array);
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quotaList);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerArrayAdapter1);




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTrainNumber = trainNumber.getText().toString();

                getFromStation = fromStation.getText().toString();
                getToStation = toStation.getText().toString();
                getDate = date.getText().toString();
                getQuota = spinner1.getSelectedItem().toString();
                 formattedMonth = "" + getMonth;
                 formattedDayOfMonth = "" + getDay;
                if (getQuota != null) {
                    upTo2Charcter = getQuota.substring(0, 2);
                }
                if (getMonth < 10) {

                    formattedMonth = "0" + getMonth;
                }
                if (getDay < 10) {

                    formattedDayOfMonth = "0" + getDay;
                }

                if (getFromStation.contains("-")) {
                    String[] stationFromParts = getFromStation.split("-");

                    first = stationFromParts[1];
                } else {
                    first = getFromStation;
                }

                if (getToStation.contains("-")) {
                    String[] StationToParts = getToStation.split("-");

                    second = StationToParts[1];
                } else {
                    second = getToStation;
                }

                if (trainNumber.length() < 5) {
                    trainNumber.setError("Train Number should be of 5 digits");
                } else if ("".equals(trainNumber)) {
                    trainNumber.setError("Please Enter Train Number");
                } else if (first.equals("")) {
                    fromStation.setError("Please enter station code..");
                } else if (second.equals("")) {
                    toStation.setError("Please enter station code..");
                } else if(formattedMonth.equals("")) {
                    date.setError("Please set date");
                }
                else{
                    VolleyCall();

                }
            }
            });
        }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        getYear = i;
        getMonth = i1 + 1;
        getDay = i2;
        date.setText(getDay + "/" + getMonth + "/" + getYear);
    }
                public void VolleyCall()
                {
                    progressDialog = new ProgressDialog(FareEnquiryActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Fetching Data..");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                            JsonKeys.FARE_URL + getTrainNumber + "/source/" + first + "/dest/" + second + "/age/18/quota/" + upTo2Charcter + "/doj/"
                                    + formattedDayOfMonth + "-" + formattedMonth + "-" + getYear + JsonKeys.apikey,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    progressDialog.hide();
                                    Intent intent = new Intent(FareEnquiryActivity.this, DetailsFareEnquiryActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("obj", response.toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
//                    intTrainNumber = Integer.valueOf(getTrainNumber);
//                    Intent intent = new Intent(FareEnquiryActivity.this, DetailsFareEnquiryActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("trainNumber", intTrainNumber);
//                    bundle.putString("day", formattedDayOfMonth);
//                    bundle.putString("month", formattedMonth);
//                    bundle.putInt("year", getYear);
//                    bundle.putString("fromStation", first);
//                    bundle.putString("toStation", second);
//                    bundle.putString("quota", getQuota);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                                }}, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(FareEnquiryActivity.this,"Railway Server responding slow.Please try again after sometime.Thankyou",Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Add a request (in this example, called stringRequest) to your RequestQueue.
                    AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
                }
}