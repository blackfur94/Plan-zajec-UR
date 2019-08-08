package com.sebastianzabrzyski.plan_zajec_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AktywnoscPlanu extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ListView listaZajec;
    SQLiteDatabase baza;
    ArrayList<String> id_zajec;
    int dzien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywnosc_plan);
        Intent in = getIntent();
        String nazwa_dnia = in.getStringExtra("com.sebastianzabrzyski.plan_zajec_2.DAY");
        setTitle(nazwa_dnia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaZajec = (ListView) findViewById(R.id.listaZajec);

        baza = openOrCreateDatabase("bazaZajec", MODE_PRIVATE, null);
        // baza.execSQL("DROP TABLE IF EXISTS Zajecia;");

        dzien = in.getIntExtra("com.sebastianzabrzyski.plan_zajec_2.ITEM_INDEX", -1);

        Button dodajZajecia = (Button) findViewById(R.id.dodajZajecia);

        dodajZajecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetailActivity = new Intent(getApplicationContext(), AktywnoscDane.class);
                showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.DZIEN", dzien);
                showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.TRYB", "dodawanie");
                startActivity(showDetailActivity);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pobierzZajecia(int dzien) {
        baza.execSQL("CREATE TABLE IF NOT EXISTS Zajecia(id integer primary key autoincrement, Nazwa VARCHAR,Rozpoczecie VARCHAR,Zakonczenie VARCHAR,Sala VARCHAR,Rodzaj VARCHAR,Tydzien VARCHAR,Prowadzacy VARCHAR,Dzien INT);");
        Cursor resultSet = baza.rawQuery("SELECT * FROM Zajecia WHERE Dzien = " + dzien + ";", null);
        int i = 0;
        resultSet.moveToFirst();

        id_zajec = new ArrayList<String>();
        ArrayList<String> nazwa_zajec = new ArrayList<String>();
        ArrayList<String> godzina_rozpoczecia = new ArrayList<String>();
        ArrayList<String> godzina_zakonczenia = new ArrayList<String>();
        ArrayList<String> tydzien = new ArrayList<String>();
        ArrayList<String> rodzaj = new ArrayList<String>();
        ArrayList<String> sala = new ArrayList<String>();
        ArrayList<String> prowadzacy = new ArrayList<String>();

        while (!resultSet.isAfterLast()) {

            id_zajec.add(resultSet.getString(resultSet.getColumnIndex("id")));
            nazwa_zajec.add(resultSet.getString(resultSet.getColumnIndex("Nazwa")));
            godzina_rozpoczecia.add(resultSet.getString(resultSet.getColumnIndex("Rozpoczecie")));
            godzina_zakonczenia.add(resultSet.getString(resultSet.getColumnIndex("Zakonczenie")));
            tydzien.add(resultSet.getString(resultSet.getColumnIndex("Tydzien")));
            rodzaj.add(resultSet.getString(resultSet.getColumnIndex("Rodzaj")));
            sala.add(resultSet.getString(resultSet.getColumnIndex("Sala")));
            prowadzacy.add(resultSet.getString(resultSet.getColumnIndex("Prowadzacy")));

            i++;
            resultSet.moveToNext();
        }

        AdapterZajec adapterZajec = new AdapterZajec(this, nazwa_zajec, godzina_rozpoczecia, godzina_zakonczenia, sala, prowadzacy, rodzaj, tydzien);
        listaZajec.setAdapter(adapterZajec);

        listaZajec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetailActivity = new Intent(getApplicationContext(), AktywnoscOpcje.class);
                showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.DZIEN", id_zajec.get(position));
                startActivity(showDetailActivity);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // this is your backendcall
        pobierzZajecia(dzien);
    }

}
