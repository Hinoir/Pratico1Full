package com.apps.hinoir.pratico1full;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class pregunta extends AppCompatActivity implements View.OnClickListener {

    //-------------------------------------------------------------
    //Referencias
    //-------------------------------------------------------------
    private EditText etRespuesta;
    private TextView tvPregunta;
    private Button bValidar;
    //-------------------------------------------------------------
    //Atributos
    //-------------------------------------------------------------
    private int x;
    private int y;
    private String operando;
    private String respuesta;
    private int punto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        //--------- Inicializar -----------------------
        etRespuesta = findViewById(R.id.et_respuesta);
        tvPregunta = findViewById(R.id.tv_pregunta);
        bValidar = findViewById(R.id.b_validar);
        punto = 0;
        //--------- PrimerLlamado ---------------------
        generarPreguntaDificil();
        bValidar.setOnClickListener(this);

    }

    private void generarPreguntaDificil(){
        Random rm = new Random();
        int i = rm.nextInt(1000)+1;
        x = 0;
        y = 0;

        if(i%2==0){
            x=rm.nextInt(200)+1;
            y=rm.nextInt(200)+1;
            while(x%y!=0){
                x=rm.nextInt(200)+1;
                y=rm.nextInt(200)+1;
            }
            operando = "/";
            respuesta=""+(x/y);
            tvPregunta.setText(""+x+operando+y);
            punto = 20;
        }
        else{
            x=rm.nextInt(200)+1;
            y=rm.nextInt(200)+1;
            operando = "x";
            respuesta =""+(x*y);
            tvPregunta.setText(""+x+operando+y);
            punto = 15;
        }
    }


    @Override
    public void onClick(View v) {
        puntaje pt = puntaje.getInstance();
        if(respuesta.equals(etRespuesta.getText().toString())){
            Toast.makeText(this,"La respuesta fue correcta, usted gano: "+punto+" puntos",Toast.LENGTH_LONG).show();
            etRespuesta.setText("");
            pt.setPuntaje(pt.getPuntaje()+punto);
            generarPreguntaDificil();
        }
        else{
            Toast.makeText(this,"La respuesta fue incorrecta, usted no gano puntos",Toast.LENGTH_LONG).show();
            etRespuesta.setText("");
            generarPreguntaDificil();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Salir").setMessage("Desea salir de la pregunta?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                ).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent j = new Intent(pregunta.this,MapsActivity.class);
                        startActivity(j);
                    }
                });

        builder.show();
    }
}
