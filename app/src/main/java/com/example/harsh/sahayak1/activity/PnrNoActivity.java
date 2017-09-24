package com.example.harsh.sahayak1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.harsh.sahayak1.Constants.JsonKeys;
import com.example.harsh.sahayak1.Controller.AppController;
import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PnrNoActivity extends AppCompatActivity {

    @BindView(R.id.train_no9) EditText pnrNumber;
    @BindView(R.id.sbmt_btn )Button submit;
    String pnrNo;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_no);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        context = PnrNoActivity.this;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pnrNo = pnrNumber.getText().toString();
                if(pnrNumber.equals(""))
                {
                    pnrNumber.setError("Please enter PNR Number.!!");
                }
                else if(pnrNumber.length()<10)
                {
                    pnrNumber.setError("Please Enter correct Pnr Number.");
                }
                else if(pnrNumber.length()>10)
                {
                    pnrNumber.setError("Please Enter correct Pnr Number.");
                }
                else
                {
                    VolleyCall();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PnrNoActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void VolleyCall()
    {
        progressDialog = new ProgressDialog(PnrNoActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JsonKeys.PNR_URL + pnrNo + JsonKeys.apikey,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        try {

                            if(response.getInt("response_code")==200)
                            {
                                progressDialog.hide();
                                Intent intent = new Intent(PnrNoActivity.this, PnrDetailsActivity.class);
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
