package com.example.harsh.sahayak1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harsh.sahayak1.Controller.AppController;
import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TrainSchedullingNumber extends AppCompatActivity {

    Button submit;
    EditText number;
    Context context;
    ProgressDialog progressDialog;
    public static final String JsonURL = "http://api.railwayapi.com/v2/route/train";
    public static final String apikey = "apikey/mghg44ea29";
    String train_number = "";

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(TrainSchedullingNumber.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        number = (EditText) findViewById(R.id.train_no9);
        submit = (Button) findViewById(R.id.sbmt_btn);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .penaltyLog() //Logs a message to LogCat
                .build());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                train_number = number.getText().toString();
                if (number.length() < 5) {
                    number.setError("Enter 5 digit Train No. ");
                } else {
                    volleycall();
                }
            }
        });
    }

    public void volleycall() {
        progressDialog = new ProgressDialog(TrainSchedullingNumber.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, JsonURL + "/" + train_number + "/" + apikey,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.hide();
                        try {
                            if (jsonObject.getInt("response_code") == 200) {
                                Intent intent = new Intent(TrainSchedullingNumber.this, TrainSchedullingActivity.class);
                                //                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle = new Bundle();
                                bundle.putString("obj", jsonObject.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else if (jsonObject.getInt("response_code") == 204) {
                                Toast.makeText(TrainSchedullingNumber.this, "Please check the Train Number and try once again.Thankyou.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TrainSchedullingNumber.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.hide();
                Toast.makeText(TrainSchedullingNumber.this, "Internet Not Available.Please Check Connection", Toast.LENGTH_LONG).show();
            }
        });
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}