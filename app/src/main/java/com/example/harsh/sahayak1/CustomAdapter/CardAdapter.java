package com.example.harsh.sahayak1.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harsh.sahayak1.R;
import com.example.harsh.sahayak1.activity.TrainScheduleGetSet;

import java.util.List;

/**
 * Created by harsh on 13-12-2016.
 */

public  class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;

    //Train Schedule List

    List<TrainScheduleGetSet> train_schedule_list;

    public CardAdapter(List<TrainScheduleGetSet> train_schedule_list, Context context)
    {
        super();
        this.context=context;
        this.train_schedule_list=train_schedule_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_schedule_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TrainScheduleGetSet getset = train_schedule_list.get(position);
        holder.textViewHalt.setText(String.valueOf(getset.getHalt()));
        holder.textViewDistance.setText(getset.getDistance());
        holder.textViewFullname.setText(getset.getFullname());
        holder.textViewDay.setText(String.valueOf(getset.getDay()));
        holder.textViewScharr.setText(getset.getScharr());
        holder.textViewSchdep.setText(getset.getSchdep());


    }

    @Override
    public int getItemCount() {
        return train_schedule_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewHalt;
        public TextView textViewDistance;
        private TextView textViewFullname;
        private TextView textViewDay;
        private TextView textViewScharr;
        private TextView textViewSchdep;

        private ViewHolder(View itemView)
        {
            super(itemView);
            textViewHalt = (TextView)itemView.findViewById(R.id.halt);
            textViewDistance = (TextView)itemView.findViewById(R.id.distance);
            textViewFullname = (TextView)itemView.findViewById(R.id.fullname);
            textViewDay = (TextView)itemView.findViewById(R.id.day);
            textViewScharr = (TextView)itemView.findViewById(R.id.sch_arr);
            textViewSchdep = (TextView)itemView.findViewById(R.id.sch_dep);
        }


    }
}

