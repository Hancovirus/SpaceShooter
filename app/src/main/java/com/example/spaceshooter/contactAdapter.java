package com.example.spaceshooter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class contactAdapter extends ArrayAdapter<String> {
    public contactAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactstringselected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.selected);
        String contact = this.getItem(position);
        if (contact != null) {
            tvSelected.setText(contact);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactstring, parent, false);
        TextView tvString = convertView.findViewById(R.id.stringContact);
        String contact = this.getItem(position);
        if (contact != null) {
            tvString.setText(contact);
        }
        return convertView;
    }
}
