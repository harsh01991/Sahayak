package com.example.harsh.sahayak1.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harsh.sahayak1.R;
import com.example.harsh.sahayak1.activity.FareEnquiryActivity;
import com.example.harsh.sahayak1.activity.LiveTrainStatusActivity;
import com.example.harsh.sahayak1.activity.PnrNoActivity;
import com.example.harsh.sahayak1.activity.SearchTrains;
import com.example.harsh.sahayak1.activity.SeatAvailabilityActivity;
import com.example.harsh.sahayak1.activity.TrainArrivalActivity;
import com.example.harsh.sahayak1.activity.TrainSchedullingNumber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder>{

    String[] values;
    Context context1;
    Integer[] icons1;


    public MainPageAdapter(Context context2,String[] values2,Integer[] icons2){

        values = values2;

        context1 = context2;

        icons1 = icons2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;
        public CardView cardView;
        public ViewHolder(View v){

            super(v);

            textView = (TextView) v.findViewById(R.id.textview1);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            cardView = (CardView) v.findViewById(R.id.cardview1);
        }
    }

    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.activity_main_page_adapter,parent,false);

        ViewHolder viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, final int position){



        Vholder.textView.setText(values[position]);
        Vholder.imageView.setImageResource(icons1[position]);

        Vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                Intent intent = new Intent(context1, TrainSchedullingNumber.class);
                    context1.startActivity(intent);

            } else if (position==1) {
                    Intent intent = new Intent(context1,LiveTrainStatusActivity.class);
                    context1.startActivity(intent);
                }
                else if(position==2)
                {
                    Intent intent = new Intent(context1, SeatAvailabilityActivity.class);
                    context1.startActivity(intent);
                }
                else if (position==3)
                {
                    Intent intent = new Intent(context1, FareEnquiryActivity.class);
                    context1.startActivity(intent);
                }
                else if(position==4)
                {
                    Intent intent = new Intent(context1, SearchTrains.class);
                    context1.startActivity(intent);
                }
                else if(position == 5)
                {
                    Intent intent = new Intent(context1, TrainArrivalActivity.class);
                    context1.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context1, PnrNoActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context1.startActivity(intent);
                }
                }
        });

    }

    @Override
    public int getItemCount(){

        return values.length;
    }
}
