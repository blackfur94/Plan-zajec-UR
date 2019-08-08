package com.sebastianzabrzyski.plan_zajec_2;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AktywnoscGlowna extends AppCompatActivity {

    ListView listaDni;
    String[] dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywnosc_glowna);
        setTitle("Wybierz dzień tygodnia");
        Resources res = getResources();
        listaDni = (ListView) findViewById(R.id.listaDni);
        dni = res.getStringArray(R.array.dni);
        AdapterDni adapterDni = new AdapterDni(this, dni);
        listaDni.setAdapter(adapterDni);

        listaDni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nazwa_dnia = dni[position];
                Intent showDetailActivity = new Intent(getApplicationContext(), AktywnoscPlanu.class);
                showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.ITEM_INDEX", position);
                showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.DAY", nazwa_dnia);
                startActivity(showDetailActivity);
            }
        });

    }
}
