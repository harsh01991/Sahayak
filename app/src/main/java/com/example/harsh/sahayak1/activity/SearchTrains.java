package com.example.harsh.sahayak1.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
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

public class SearchTrains extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.searchTrainFrom) AutoCompleteTextView stationFrom;
    @BindView(R.id.searchTrainTo) AutoCompleteTextView stationTo;
    @BindView(R.id.pickedDate) EditText pickedDate;
    @BindView(R.id.seatAvailSbmtBtn) Button submitButton;

    int day, month, year;
    int getYear, getMonth, getDay;
    ProgressDialog progressDialog;
    String formattedMonth,formattedDayOfMonth;
    Context context;

    String  getFromStation = "", getToStation = "", getDate = "",first = "",second = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trains);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        context=SearchTrains.this;

        Resources resources = getResources();
        String[] stationCodes = resources.getStringArray(R.array.stationCodeList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, stationCodes);
        stationFrom.setThreshold(1);
        stationTo.setThreshold(1);
        stationFrom.setAdapter(arrayAdapter);
        stationTo.setAdapter(arrayAdapter);


        pickedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchTrains.this, SearchTrains.this,
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getFromStation = stationFrom.getText().toString();
                getToStation = stationTo.getText().toString();
                getDate = pickedDate.getText().toString();
                formattedMonth = "" + getMonth;
                formattedDayOfMonth = "" + getDay;

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

                 if (first.equals("")) {
                    stationFrom.setError("Please enter station code..");
                } else if (second.equals("")) {
                    stationTo.setError("Please enter station code..");
                } else {
                     VolleyCall();
//                    Intent intent = new Intent(SearchTrains.this, DetailsSearchTrainActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("day", formattedDayOfMonth);
//                    bundle.putString("month", formattedMonth);
//                    bundle.putInt("year", getYear);
//                    bundle.putString("fromStation", first);
//                    bundle.putString("toStation", second);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }
            }
        });


    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        getYear = i;
        getMonth = i1 + 1;
        getDay = i2;
        pickedDate.setText(getDay + "/" + getMonth + "/" + getYear);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchTrains.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void VolleyCall()
    {
        progressDialog = new ProgressDialog(SearchTrains.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JsonKeys.SEARCH_URL + first + "/dest/" + second +
                        "/date/" + formattedDayOfMonth+ "-" + formattedMonth + "-" + getYear + JsonKeys.apikey,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        try {

                            if(response.getInt("response_code")==200)
                            {
                                progressDialog.hide();
                                Intent intent = new Intent(SearchTrains.this, DetailsSearchTrainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("obj", response.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }


                            else
                            {

                                Toast.makeText(context,response.getString("error"),Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(context,"Internet Not Available.Please Check Connection",Toast.LENGTH_LONG).show();
            }
        });
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }
}
