package com.example.harsh.sahayak1.activity;

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
import android.widget.Spinner;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainArrivalActivity extends AppCompatActivity {

    @BindView(R.id.stationCode) AutoCompleteTextView stationCode;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.submitButton) Button submit;
    ProgressDialog progressDialog;
    String stationName="",first = "";
    String hours;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrival);
        ButterKnife.bind(this);

        context = TrainArrivalActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Resources resources = getResources();
        String[] stationCodes = resources.getStringArray(R.array.stationCodeList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, stationCodes);
        stationCode.setThreshold(1);
        stationCode.setAdapter(arrayAdapter);


        String[] uptoHours = resources.getStringArray(R.array.hours_array);
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uptoHours);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              stationName = stationCode.getText().toString();
                hours = spinner.getSelectedItem().toString();
                if(stationName.equals("")) {
                    stationCode.setError("Please Select Any Station");
                }
                  else if (stationName.contains("-")) {
                        String[] stationFromParts = stationName.split("-");
                        first = stationFromParts[1];
                    VolleyCall();
                    } else {
                        first = stationName;
                    VolleyCall();

                }


            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TrainArrivalActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void VolleyCall()
    {
        progressDialog = new ProgressDialog(TrainArrivalActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JsonKeys.ARRIVAL_URL + first+ "/hours/" + hours + JsonKeys.apikey, hours,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        try {
                            if(response.getInt("response_code")==200)
                            {
                                progressDialog.hide();
                                Intent intent = new Intent(TrainArrivalActivity.this, DetailsTrainArrivalActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("obj", response.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }


                            else
                            {

                                Toast.makeText(context,response.getString("error"),Toast.LENGTH_LONG).show();
                            }



                        }
                        catch (JSONException e)
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