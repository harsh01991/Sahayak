package com.example.harsh.sahayak1.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harsh.sahayak1.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by harsh on 31-01-2017.
 */

public class TrainArrivalStationAdapter extends RecyclerView.Adapter<TrainArrivalStationAdapter.ViewHolder>{

    HashMap<String,String> hashMap1;
    Context context1;

    public TrainArrivalStationAdapter(Context context2,HashMap<String,String> hashMap2)
    {
        this.context1 = context2;
        this.hashMap1 = hashMap2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView trainName,trainNumber,schArr,schDep,actArr,actDep,delayArr,delayDep;
        public ViewHolder(View v)
        {
            super(v);
            trainName=(TextView)v.findViewById(R.id.trainName);
            trainNumber=(TextView)v.findViewById(R.id.trainNumber);
            schArr = (TextView)v.findViewById(R.id.schTime);
            schDep = (TextView)v.findViewById(R.id.schTime2);
            actArr = (TextView)v.findViewById(R.id.expectTime);
            actDep = (TextView)v.findViewById(R.id.expectTime2);
            delayArr = (TextView)v.findViewById(R.id.arrTime);
            delayDep = (TextView)v.findViewById(R.id.depTime);

        }
    }

    @Override
    public TrainArrivalStationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.train_arival_at_station_view_file,parent,false);

        TrainArrivalStationAdapter.ViewHolder viewHolder1 = new TrainArrivalStationAdapter.ViewHolder(view1);

        return viewHolder1;
    }
    @Override
    public void onBindViewHolder(TrainArrivalStationAdapter.ViewHolder holder, int position){

        try
        {
            JSONArray jsonArray = new JSONArray(hashMap1.get("train"));
            Log.i("search", "onBindViewHolder: " + position);
            System.out.print(position);
            holder.trainNumber.setText(jsonArray.getJSONObject(position).getString("number"));
            holder.trainName.setText(jsonArray.getJSONObject(position).getString("name"));
            holder.schArr.setText(jsonArray.getJSONObject(position).getString("scharr"));
            holder.schDep.setText(jsonArray.getJSONObject(position).getString("schdep"));
            holder.actArr.setText(jsonArray.getJSONObject(position).getString("actarr"));
            holder.actArr.setText(jsonArray.getJSONObject(position).getString("actdep"));
            holder.delayArr.setText(jsonArray.getJSONObject(position).getString("delayarr"));
            holder.delayDep.setText(jsonArray.getJSONObject(position).getString("delaydep"));
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        int size=0;
        try {
            size =  new JSONArray(hashMap1.get("train")).length();
            Log.i("size",size+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return size;
    }

}


