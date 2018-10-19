package com.tez.popuptez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Menu extends AppCompatActivity {
    Button BtnEjercicios,BtnActividad,BtnDeporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        BtnEjercicios=(Button)findViewById(R.id.BtnEjercicio);
        BtnActividad=(Button)findViewById(R.id.BtnActividad);
        BtnDeporte=(Button)findViewById(R.id.BtnDeporte);



        BtnActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActividad();
            }
        });

        BtnEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenEjercicio();
            }
        });

        BtnDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDeporte();
            }
        });
    }
    public void OpenEjercicio(){
        Intent intent= new Intent(Menu.this,Ejercicios.class );
        startActivity(intent);
    }
    public void OpenActividad(){
        Intent intent= new Intent(Menu.this,Actividad.class );
        startActivity(intent);
    }
    public void OpenDeporte(){
        Intent intent= new Intent(Menu.this,Deportes.class );
        startActivity(intent);
    }
}

