package com.pasperdu.app.wapjwennli.add;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pasperdu.app.wapjwennli.R;
import com.pasperdu.app.wapjwennli.models.Piece;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterObjetRetrouve extends AppCompatActivity {
    EditText tvObjetRetrouve, TvNameProprietaite,lieuRetrouve,tvTelephoneContacte;
    Button btnAddObjet;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_retrouve);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ajouter un objet retrouve ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvObjetRetrouve = (EditText) findViewById(R.id.tvObjetRetrouve);
        TvNameProprietaite = (EditText) findViewById(R.id.TvNameProprietaite);
        lieuRetrouve = (EditText) findViewById(R.id.lieuRetrouve);
        tvTelephoneContacte = (EditText) findViewById(R.id.tvTelephoneContacte);
        btnAddObjet = (Button) findViewById(R.id.btnAddObjet);

        btnAddObjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addObjectRetrouve();
            }
        });

    }
    public void addObjectRetrouve() {

        showLoading();

        Piece newCompany = new Piece();

        final String name = tvObjetRetrouve.getText().toString();
        final String adresse = lieuRetrouve.getText().toString();
        final String email = TvNameProprietaite.getText().toString();
        final String telephone = tvTelephoneContacte.getText().toString();

        newCompany.setTypePiece(name);
        newCompany.setLieuPiece(adresse);
        newCompany.setNamePiece(email);
        newCompany.setTelephoneReference(telephone);


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date currentdate = new Date();
        newCompany.setDateAjout(currentdate);

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString().substring(4);
        newCompany.setColor(ts);

        // sendpicture(selectedImage);

        String nullChamps = "";
        if (name.equals(nullChamps) || adresse.equals(nullChamps) || email.equals(nullChamps) || telephone.equals(nullChamps)) {

            Toast.makeText(AjouterObjetRetrouve.this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
            dimissLoading();

        } else {
            //newUnite.setEquivalent_Unite(equivalent_unite);
            //Backendless api to save a new unite
            Backendless.Persistence.save(newCompany, new AsyncCallback<Piece>() {
                @Override
                public void handleResponse(Piece response) {

                    dimissLoading();
                    finish();
                    /*
                    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
                    myFirebaseMessagingService.sendNotification(name);
                    */
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(AjouterObjetRetrouve.this, "Verifiez votre connexion internet", Toast.LENGTH_SHORT).show();
                    showLoading();
                }
            });
        }
    }
    // Des dialog
    public void showLoading(){
        progress = ProgressDialog.show(this, "Ajout en cours","Patientez...",false,false);
    }
    public void dimissLoading(){
        progress.dismiss();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
