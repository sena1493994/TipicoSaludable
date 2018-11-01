package com.sanchez.tipicosaludable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanchez.tipicosaludable.model.Perfil;

import java.util.UUID;

public class CaloriasActivity extends AppCompatActivity {

    EditText peso,talla,edad;
    Button validar;
    RadioButton masc,fem,act1,act2,act3,act4,act5;
    RadioGroup genero,contex,actividad;
    public static double imc,mb,actmb;
    public static  int check;
    double a,b,c;

    //google
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser firebaseUser;
    public static int temp =0;
    FirebaseDatabase firebaseDatabase;
    FirebaseApp firebaseApp;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorias);


        validar = (Button) findViewById(R.id.validar);
        inicializarFirebase();

        inicializar();

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarTodo();
                gastarKal();



                Perfil p = new Perfil();
                p.setUid(UUID.randomUUID().toString());
                if (act1.isChecked()){
                    p.setActividad(act1.getText().toString());
                }

                if (act2.isChecked()){
                    p.setActividad(act2.getText().toString());
                }

                if (act3.isChecked()){
                    p.setActividad(act3.getText().toString());
                }

                if (act4.isChecked()){
                    p.setActividad(act4.getText().toString());
                }

                if (act5.isChecked()){
                    p.setActividad(act5.getText().toString());
                }

                p.setCalorías_maximas(actmb);
                p.setContextura("sedentario");

                p.setEdad(edad.getText().toString());
                if (masc.isChecked()){
                    p.setGenero(masc.getText().toString());

                }else if (fem.isChecked()){
                    p.setGenero(fem.getText().toString());

                }
                p.setImc(imc);
                p.setPeso(peso.getText().toString());
                p.setTalla(talla.getText().toString());
                databaseReference.child("Perfil").child(p.getUid()).setValue(p);





            }
        });


    }

    private void inicializarFirebase() {
        firebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializar() {

        peso=findViewById(R.id.peso);
        talla=findViewById(R.id.talla);
        edad=findViewById(R.id.edad);
        validar=findViewById(R.id.validar);
        act1=findViewById(R.id.act1);
        act2=findViewById(R.id.act2);
        act3=findViewById(R.id.act3);
        act4=findViewById(R.id.act4);
        act5=findViewById(R.id.act5);
        masc=findViewById(R.id.gmen);
        fem=findViewById(R.id.gfem);
        genero=findViewById(R.id.genero);
        actividad=findViewById(R.id.actividad);

    }
    /* Metodo de validacion de campos de texto, radiobuttons*/

    private void validarTodo() {

        int validar = 0;

        if (peso.getText().toString().length()>0){
            a=Double.parseDouble(peso.getText().toString());

            validar++;
        }else {
            peso.setError("Falta campo");
        }
        if (talla.getText().toString().length()>0){
            b=Double.parseDouble(talla.getText().toString());

            validar++;
        }else {
            talla.setError("Falta campo");
        }
        if (edad.getText().toString().length()>0){
            c=Double.parseDouble(edad.getText().toString());

            validar++;
        }else {
            edad.setError("Falta campo");
        }

        if (masc.isChecked() | fem.isChecked()){
            validar++;
            /* Formula Tasa Metabolica Basal segùn genero*/
            if (masc.isChecked()){
                check = 1;

                mb= (66.4730 + (13.7516 * a) + (5.0033 * b) - (6.7759 * c));
            }else {
                check =0;
                mb= (665.0955 + (9.5634 * a) + (1.8496 * b) - (4.6756 * c));
            }
        }else{
            Toast.makeText(this, "Falta seleccionar", Toast.LENGTH_SHORT).show();
        }

        if (act1.isChecked() | act2.isChecked() | act3.isChecked() | act4.isChecked() | act5.isChecked()){
            validar++;
        }else{
            Toast.makeText(this, "Falta seleccionar", Toast.LENGTH_SHORT).show();
        }

        if (validar == 5){
        /* Formula Indice de Masa Corporal y discriminación tipo de obesidad*/
            double d=b/100;
            imc=a/(d*d);

            temp =1;
            Intent intent = new Intent(CaloriasActivity.this,Menu_Lateral.class);
            startActivity(intent);
            finish();
            //6Toast.makeText(CaloriasActivity.this, "" + imc, Toast.LENGTH_SHORT).show();

        }
    }

    /* Metodo para discriminar consumo de Kcal de acuerdo a el nivel de actividad*/
    public void gastarKal(){
        if (act1.isChecked()){
            actmb=mb*1.9;
        }

        if (act2.isChecked()){
            actmb=mb*1.725;
        }

        if (act3.isChecked()){
            actmb=mb*1.55;
        }

        if (act4.isChecked()){
            actmb=mb*1.375;
        }

        if (act5.isChecked()){
            actmb=mb*1.2;
        }
    }



}
