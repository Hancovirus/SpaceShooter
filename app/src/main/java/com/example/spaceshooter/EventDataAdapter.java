package com.example.spaceshooter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class EventDataAdapter extends BaseAdapter {
    private EventWebServiceFragment activity;
    private List<Mission> missions;

    public EventDataAdapter(EventWebServiceFragment activity, List<Mission> missions) {
        this.activity = activity;
        this.missions = missions;
    }

    @Override
    public int getCount() {
        return missions.size();
    }

    @Override
    public Object getItem(int position) {
        return missions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.mission_item, null);

        TextView missionName = convertView.findViewById(R.id.mission_name);
        missionName.setText(missions.get(position).getName());

        TextView missionDes = convertView.findViewById(R.id.mission_description);
        missionDes.setText(missions.get(position).getDescription());

        ImageButton button = convertView.findViewById(R.id.imageButton);
        if(!missions.get(position).isComplete()){
            button.setOnClickListener(v -> {
                EventWebServiceFragment.completeMission(missions.get(position).getId());
                button.setImageResource(R.drawable.complete);
                button.setEnabled(false); // Disable the button after completion
            });
        }
        else {
            button.setImageResource(R.drawable.complete);
            button.setEnabled(false);
        }
        return convertView;
    }
}
