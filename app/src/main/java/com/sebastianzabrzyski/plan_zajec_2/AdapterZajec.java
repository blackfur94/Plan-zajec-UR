package com.sebastianzabrzyski.plan_zajec_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterZajec extends BaseAdapter {


    LayoutInflater mInflater;
    ArrayList<String> zajecia = new ArrayList<>();
    ArrayList<String> godzinaRozpoczecia = new ArrayList<>();
    ArrayList<String> godzinaZakonczenia = new ArrayList<>();
    ArrayList<String> sala = new ArrayList<>();
    ArrayList<String> prowadzacy = new ArrayList<>();
    ArrayList<String> typZajec = new ArrayList<>();
    ArrayList<String> tydzien = new ArrayList<>();

    public AdapterZajec(Context c, ArrayList<String> z, ArrayList<String> gr, ArrayList<String> gz, ArrayList<String> s, ArrayList<String> p, ArrayList<String> tz, ArrayList<String> tyd) {
        zajecia = z;
        godzinaRozpoczecia = gr;
        godzinaZakonczenia = gz;
        sala = s;
        prowadzacy = p;
        typZajec = tz;
        tydzien = tyd;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return zajecia.size();
    }

    @Override
    public Object getItem(int position) {
        return zajecia.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.widok_zajecia, null);
        TextView zajeciaTextView = (TextView) v.findViewById(R.id.nazwaZajec);
        TextView godzinaRozpoczeciaTextView = (TextView) v.findViewById(R.id.godzinaRozpoczecia);
        TextView godzinaZakonczeniaTextView = (TextView) v.findViewById(R.id.godzinaZakonczenia);
        TextView salaTextView = (TextView) v.findViewById(R.id.sala);
        TextView prowadzacyTextView = (TextView) v.findViewById(R.id.prowadzacy);
        TextView typZajecTextView = (TextView) v.findViewById(R.id.rodzajZajec);
        TextView tydzienTextView = (TextView) v.findViewById(R.id.tydzien);

        String zajecia2 = zajecia.get(position);
        String godzinaRzpoczecia2 = godzinaRozpoczecia.get(position);
        String godzinaZakonczenia2 = godzinaZakonczenia.get(position);
        String sala2 = sala.get(position);
        String prowadzacy2 = prowadzacy.get(position);
        String typZajec2 = typZajec.get(position);
        String tydzien2 = tydzien.get(position);

        zajeciaTextView.setText(zajecia2);
        godzinaRozpoczeciaTextView.setText(godzinaRzpoczecia2);
        godzinaZakonczeniaTextView.setText(godzinaZakonczenia2);
        salaTextView.setText(sala2);
        prowadzacyTextView.setText(prowadzacy2);
        typZajecTextView.setText(typZajec2);
        tydzienTextView.setText(tydzien2);

        return v;
    }
}
