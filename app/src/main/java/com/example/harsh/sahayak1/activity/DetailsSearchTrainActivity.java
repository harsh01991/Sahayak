package com.example.harsh.sahayak1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.harsh.sahayak1.CustomAdapter.SearchTrainAdapter;
import com.example.harsh.sahayak1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DetailsSearchTrainActivity extends AppCompatActivity {
    
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    ArrayList daysCodeList,runsOnList;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_search_train);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        hashMap = new HashMap<>();
        daysCodeList = new ArrayList();
        runsOnList = new ArrayList();

        //Taking Values from Search Trains Class Through Bundle
        Bundle bundle = getIntent().getExtras();

        //Assigning Recycler View to this context
        context = DetailsSearchTrainActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


                        try {
                            JSONObject response = new JSONObject(bundle.getString("obj"));
                            if(response.getInt("response_code")==200) {
                                Iterator<String> keys = response.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    String value = response.getString(key);
                                    hashMap.put(key, value);
                                }
                                adapter = new SearchTrainAdapter(context, hashMap);
                                recyclerView.setAdapter(adapter);
                                Log.i("HashMap",hashMap.toString());
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsSearchTrainActivity.this,SearchTrains.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    }
