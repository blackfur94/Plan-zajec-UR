package com.sebastianzabrzyski.plan_zajec_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterDni extends BaseAdapter {

    LayoutInflater mInflater;
    String[] dni;

    public AdapterDni(Context c, String[] d) {

        dni = d;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dni.length;
    }

    @Override
    public Object getItem(int position) {
        return dni[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.widok_dni, null);
        TextView nazwaDni = (TextView) v.findViewById(R.id.nazwaDnia);
        String nazwaDnia = dni[position];

        nazwaDni.setText(nazwaDnia);
        return v;
    }
}
