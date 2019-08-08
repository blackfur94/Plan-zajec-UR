package com.sebastianzabrzyski.plan_zajec_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AktywnoscOpcje extends AppCompatActivity {

    SQLiteDatabase baza;
    String dzien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywnosc_opcje);
        setTitle("Wybierz opcję");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baza = openOrCreateDatabase("bazaZajec", MODE_PRIVATE, null);
        Intent in = getIntent();
        dzien = in.getStringExtra("com.sebastianzabrzyski.plan_zajec_2.DZIEN");
        Button przyciskUsuwania = (Button) findViewById(R.id.przyciskUsuwania);
        Button przyciskEdycji = (Button) findViewById(R.id.przyciskEdycji);

        przyciskUsuwania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usunZajecia(dzien);
            }
        });

        przyciskEdycji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edytujZajecia(dzien);
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

    private void usunZajecia(final String id_zajec) {

        AlertDialog alertDialog = new AlertDialog.Builder(AktywnoscOpcje.this).create();
        alertDialog.setTitle("Potwierdzenie");
        alertDialog.setMessage("Usunąć zajęcia?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        baza.execSQL("DELETE FROM Zajecia WHERE id = " + id_zajec + ";");
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nie",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


    }

    private void edytujZajecia(String id_zajec) {
        Intent showDetailActivity = new Intent(getApplicationContext(), AktywnoscDane.class);
        showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.DZIEN", dzien);
        showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.ID", id_zajec);
        showDetailActivity.putExtra("com.sebastianzabrzyski.plan_zajec_2.TRYB", "edycja");

        startActivityForResult(showDetailActivity, 1);

        // startActivity(showDetailActivity);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            finish();
        }
    }

}
