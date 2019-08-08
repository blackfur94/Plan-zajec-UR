package com.sebastianzabrzyski.plan_zajec_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AktywnoscDane extends AppCompatActivity {

    SQLiteDatabase baza;
    int dzien;
    String tryb, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywnosc_dane);
        setTitle("Uzupełnij dane o zajęciach");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent in = getIntent();
        dzien = in.getIntExtra("com.sebastianzabrzyski.plan_zajec_2.DZIEN", -1);
        tryb = in.getStringExtra("com.sebastianzabrzyski.plan_zajec_2.TRYB");
        id = in.getStringExtra("com.sebastianzabrzyski.plan_zajec_2.ID");
        Resources res = getResources();

        final Spinner wyborZajec = findViewById(R.id.rodzajZajec);
        String[] rodzajZajec = res.getStringArray(R.array.rodzajZajec);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rodzajZajec);
        wyborZajec.setAdapter(adapter);

        final Spinner wyborTygodnia = findViewById(R.id.tydzien);
        String[] tydzien = res.getStringArray(R.array.tydzien);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tydzien);
        wyborTygodnia.setAdapter(adapter2);

        final Spinner godzinaRozpoczecia = findViewById(R.id.rozpoczecie);
        String[] godziny_r = res.getStringArray(R.array.godzinyRozpoczecia);
        String[] godziny_z = res.getStringArray(R.array.godzinyZakonczenia);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, godziny_r);
        godzinaRozpoczecia.setAdapter(adapter3);

        final Spinner godzinaZakonczenia = findViewById(R.id.zakonczenie);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, godziny_z);
        godzinaZakonczenia.setAdapter(adapter4);
        final TextView nazwaZajec = (TextView) findViewById(R.id.nazwaZajec);
        final TextView prowadzacy = (TextView) findViewById(R.id.prowadzacy);
        final TextView sala = (TextView) findViewById(R.id.sala);

        if (tryb.equals("edycja")) {

            baza = openOrCreateDatabase("bazaZajec", MODE_PRIVATE, null);
            Cursor resultSet = baza.rawQuery("SELECT * FROM Zajecia WHERE id = " + id + ";", null);
            int i = 0;
            resultSet.moveToFirst();

            while (!resultSet.isAfterLast()) {

                String nazwa_bd = resultSet.getString(resultSet.getColumnIndex("Nazwa"));
                String rozpoczecie_bd = resultSet.getString(resultSet.getColumnIndex("Rozpoczecie"));
                String zakonczenie_bd = resultSet.getString(resultSet.getColumnIndex("Zakonczenie"));
                String tydzien_bd = resultSet.getString(resultSet.getColumnIndex("Tydzien"));
                String rodzaj_bd = resultSet.getString(resultSet.getColumnIndex("Rodzaj"));
                String sala_bd = resultSet.getString(resultSet.getColumnIndex("Sala"));
                String prowadzacy_bd = resultSet.getString(resultSet.getColumnIndex("Prowadzacy"));

                nazwaZajec.setText(nazwa_bd);
                prowadzacy.setText(prowadzacy_bd);
                sala.setText(sala_bd);

                godzinaRozpoczecia.setSelection(getIndex(godzinaRozpoczecia, rozpoczecie_bd));
                godzinaZakonczenia.setSelection(getIndex(godzinaZakonczenia, zakonczenie_bd));
                wyborTygodnia.setSelection(getIndex(wyborTygodnia, tydzien_bd));
                wyborZajec.setSelection(getIndex(wyborZajec, rodzaj_bd));

                i++;
                resultSet.moveToNext();
            }
        }

        Button dodajZajecia = (Button) findViewById(R.id.dodajZajecia2);

        dodajZajecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nazwaZajecStr = nazwaZajec.getText().toString();
                String salaStr = sala.getText().toString();
                String prowadzacyStr = prowadzacy.getText().toString();
                String rozpoczecieStr = godzinaRozpoczecia.getSelectedItem().toString();
                String zakonczenieStr = godzinaZakonczenia.getSelectedItem().toString();
                String rodzajZajecStr = wyborZajec.getSelectedItem().toString();
                String tydzienStr = wyborTygodnia.getSelectedItem().toString();

                int indeksRozpoczecie = godzinaRozpoczecia.getSelectedItemPosition();
                int indeksZakonczenie = godzinaZakonczenia.getSelectedItemPosition();
                int roznicaIndeks = indeksZakonczenie - indeksRozpoczecie;


                if (nazwaZajecStr.equals("") || prowadzacyStr.equals("") || salaStr.equals("")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(AktywnoscDane.this).create();
                    alertDialog.setTitle("Błąd");
                    alertDialog.setMessage("Nie wprowadzono wszystkich informacji");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else if (roznicaIndeks < 0) {

                    AlertDialog alertDialog = new AlertDialog.Builder(AktywnoscDane.this).create();
                    alertDialog.setTitle("Błąd");
                    alertDialog.setMessage("Nieprawidłowy czas trwania zajęć");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else if (tryb.equals("edycja")) {

                    {
                        baza.execSQL("UPDATE Zajecia SET Nazwa ='" + nazwaZajecStr +
                                "', Sala ='" + salaStr +
                                "', Prowadzacy ='" + prowadzacyStr +
                                "', Rozpoczecie ='" + rozpoczecieStr +
                                "', Zakonczenie ='" + zakonczenieStr +
                                "', Rodzaj ='" + rodzajZajecStr +
                                "', Tydzien ='" + tydzienStr +
                                "' WHERE id = '" + id + "';");
                        setResult(2);
                        finish();
                    }

                } else {

                    baza = openOrCreateDatabase("bazaZajec", MODE_PRIVATE, null);
                    baza.execSQL("INSERT INTO Zajecia VALUES(NULL, '" + nazwaZajecStr + "','" +
                            rozpoczecieStr + "','" +
                            zakonczenieStr + "','" +
                            salaStr + "','" +
                            rodzajZajecStr + "','" +
                            tydzienStr + "','" +
                            prowadzacyStr + "','" +
                            dzien + "');");
                    finish();

                }

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

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

}
