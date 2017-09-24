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
import java.util.StringTokenizer;

/**
 * Created by harsh on 27-01-2017.
 */

public class SearchTrainAdapter extends RecyclerView.Adapter<SearchTrainAdapter.ViewHolder> {

    HashMap<String,String> hashMap1;
    Context context1;

    String fadeInOrOut="";
public SearchTrainAdapter(Context context2, HashMap<String,String> hashMap2)
{
    this.context1 = context2;
    this.hashMap1 = hashMap2;
}
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView trainNumber,trainName,sun,mon,tues,wed,thurs,fri,sat,travelTime,srcDepTime,srcStationCode,desArrTime,desStationCode;
        public ViewHolder(View v)
        {
            super(v);
            trainNumber = (TextView)v.findViewById(R.id.trainNumber);
            trainName = (TextView) v.findViewById(R.id.trainName);
            sun = (TextView) v.findViewById(R.id.sunday);
            mon = (TextView) v.findViewById(R.id.monday);
            tues = (TextView) v.findViewById(R.id.tuesday);
            wed = (TextView) v.findViewById(R.id.wednesday);
            thurs = (TextView) v.findViewById(R.id.thursday);
            fri = (TextView) v.findViewById(R.id.friday);
            sat = (TextView) v.findViewById(R.id.saturday);
            travelTime = (TextView) v.findViewById(R.id.travelTime);
            srcDepTime = (TextView) v.findViewById(R.id.srcDepTime);
            srcStationCode = (TextView) v.findViewById(R.id.srcStationCode);
            desArrTime = (TextView) v.findViewById(R.id.desArrTime);
            desStationCode = (TextView) v.findViewById(R.id.desStationCode);

        }
    }
    @Override
    public SearchTrainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.seach_train_view_file,parent,false);

        SearchTrainAdapter.ViewHolder viewHolder1 = new SearchTrainAdapter.ViewHolder(view1);

        return viewHolder1;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        try
        {
        JSONArray jsonArray = new JSONArray(hashMap1.get("train"));
            Log.i("search", "onBindViewHolder: " + position);
            System.out.print(position);
            StringTokenizer st = new StringTokenizer(jsonArray.getJSONObject(position).getString("travel_time"));
            String h = st.nextToken(":");
            String m = st.nextToken();
        holder.trainNumber.setText(jsonArray.getJSONObject(position).getString("number"));
        holder.trainName.setText(jsonArray.getJSONObject(position).getString("name"));
        holder.desArrTime.setText(jsonArray.getJSONObject(position).getString("dest_arrival_time"));
        holder.travelTime.setText(h+"h "+m+"m");
        holder.srcDepTime.setText(jsonArray.getJSONObject(position).getString("src_departure_time"));
            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(0).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.sun.setEnabled(false);
            }

        fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(1).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.mon.setEnabled(false);
            }

            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(2).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.tues.setEnabled(false);
            }

            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(3).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.wed.setEnabled(false);
            }

            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(4).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.thurs.setEnabled(false);
            }

            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(5).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.fri.setEnabled(false);
            }

            fadeInOrOut = jsonArray.getJSONObject(position).getJSONArray("days").getJSONObject(6).getString("runs");
            if(fadeInOrOut.equals("N"))
            {
                holder.sat.setEnabled(false);
            }

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return size;
    }

}
