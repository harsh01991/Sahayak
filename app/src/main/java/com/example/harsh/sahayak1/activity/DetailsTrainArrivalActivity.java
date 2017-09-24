package com.example.harsh.sahayak1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.sahayak1.CustomAdapter.TrainArrivalStationAdapter;
import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsTrainArrivalActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    HashMap<String, String> hashMap;
    @BindView(R.id.totalTrain)
    TextView totalTrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_train_arrival);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        hashMap = new HashMap<>();

        Bundle bundle = getIntent().getExtras();

        //Assigning Recycler View to this context
        context = DetailsTrainArrivalActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);




                        try {
                            JSONObject response = new JSONObject(bundle.getString("obj"));
                            if(response.getInt("response_code")==200)
                            {
                                int total =response.getInt("total");
                                totalTrain.setText(String.valueOf(total));
                                Iterator<String> keys = response.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    String value = response.getString(key);
                                    hashMap.put(key, value);
                                }

                                    adapter = new TrainArrivalStationAdapter(context, hashMap);
                                    recyclerView.setAdapter(adapter);
                                    Log.i("HashMap", hashMap.toString());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsTrainArrivalActivity.this,TrainArrivalActivity.class);
     //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    }

