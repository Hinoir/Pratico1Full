package com.apps.hinoir.pratico1full;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class tienda extends AppCompatActivity {
    //-------------------------------------------------------------
    //Referencias
    //-------------------------------------------------------------
    private TextView tvTPuntos;
    private TextView tvCodigo;
    private TextView tvProducto1;
    private Button bCompra1;
    private TextView tvProducto2;
    private Button bCompra2;
    private TextView tvProducto3;
    private Button bCompra3;
    private TextView tvProducto4;
    private Button bCompra4;
    private TextView tvProducto5;
    private Button bCompra5;
    //-------------------------------------------------------------
    //Atributos
    //-------------------------------------------------------------
    private String n1;
    private String n2;
    private String n3;
    private String n4;
    private String n5;
    private int pr1;
    private int pr2;
    private int pr3;
    private int pr4;
    private int pr5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        //--------- Inicializar -----------------------
        tvTPuntos = findViewById(R.id.tv_tpuntos);
        tvCodigo = findViewById(R.id.tv_codigo);
        tvProducto1 = findViewById(R.id.tv_p1);
        tvProducto2 = findViewById(R.id.tv_p2);
        tvProducto3 = findViewById(R.id.tv_p3);
        tvProducto4 = findViewById(R.id.tv_p4);
        tvProducto5 = findViewById(R.id.tv_p5);
        bCompra1 = findViewById(R.id.b_c1);
        bCompra2 = findViewById(R.id.b_c2);
        bCompra3 = findViewById(R.id.b_c3);
        bCompra4 = findViewById(R.id.b_c4);
        bCompra5 = findViewById(R.id.b_c5);
        n1="Lapicero Icesi";
        n2="Cuaderno";
        n3="Libreta Icesi";
        n4="Camiseta ";
        n5="Saco Icesi";
        pr1=35;
        pr2=50;
        pr3=70;
        pr4=90;
        pr5=150;
        tvProducto1.setText(n1+" $"+pr1);
        tvProducto2.setText(n2+" $"+pr2);
        tvProducto3.setText(n3+" $"+pr3);
        tvProducto4.setText(n4+" $"+pr4);
        tvProducto5.setText(n5+" $"+pr5);

        final puntaje pt = puntaje.getInstance();
        tvTPuntos.setText("Total puntos: "+pt.getPuntaje());

        bCompra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pt.getPuntaje()>=pr1){
                    Random rm = new Random();
                    int x=rm.nextInt(10)+1;
                    int y=rm.nextInt(10)+1;
                    int z=rm.nextInt(10)+1;
                    pt.setPuntaje(pt.getPuntaje()-pr1);
                    tvTPuntos.setText("Total puntos: "+pt.getPuntaje());
                    tvCodigo.setText("Su codigo de canje es: "+x+"L"+y+"I");
                }
                else{
                    Toast.makeText(tienda.this,"Puntos insuficientes",Toast.LENGTH_LONG).show();
                }
            }
        });
        bCompra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pt.getPuntaje()>=pr2){
                    Random rm = new Random();
                    int x=rm.nextInt(20)+10;
                    int y=rm.nextInt(20)+10;
                    int z=rm.nextInt(20)+10;
                    pt.setPuntaje(pt.getPuntaje()-pr2);
                    tvTPuntos.setText("Total puntos: "+pt.getPuntaje());
                    tvCodigo.setText("Su codigo de canje es: "+x+"C"+y+"I"+z);
                }
                else{
                    Toast.makeText(tienda.this,"Puntos insuficientes",Toast.LENGTH_LONG).show();
                }
            }
        });
        bCompra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pt.getPuntaje()>=pr3){
                    Random rm = new Random();
                    int x=rm.nextInt(30)+20;
                    int y=rm.nextInt(30)+20;
                    int z=rm.nextInt(30)+20;
                    pt.setPuntaje(pt.getPuntaje()-pr3);
                    tvTPuntos.setText("Total puntos: "+pt.getPuntaje());
                    tvCodigo.setText("Su codigo de canje es: "+x+"LB"+y+"I"+z);
                }
                else{
                    Toast.makeText(tienda.this,"Puntos insuficientes",Toast.LENGTH_LONG).show();
                }
            }
        });
        bCompra4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pt.getPuntaje()>=pr4){
                    Random rm = new Random();
                    int x=rm.nextInt(40)+30;
                    int y=rm.nextInt(40)+30;
                    int z=rm.nextInt(40)+30;
                    pt.setPuntaje(pt.getPuntaje()-pr4);
                    tvTPuntos.setText("Total puntos: "+pt.getPuntaje());
                    tvCodigo.setText("Su codigo de canje es: "+x+"CS"+y+"I"+z);
                }
                else{
                    Toast.makeText(tienda.this,"Puntos insuficientes",Toast.LENGTH_LONG).show();
                }
            }
        });
        bCompra5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pt.getPuntaje()>=pr5){
                    Random rm = new Random();
                    int x=rm.nextInt(50)+40;
                    int y=rm.nextInt(50)+40;
                    int z=rm.nextInt(50)+40;
                    pt.setPuntaje(pt.getPuntaje()-pr5);
                    tvTPuntos.setText("Total puntos: "+pt.getPuntaje());
                    tvCodigo.setText("Su codigo de cange es:"+x+"S"+y+"I"+z);
                }
                else{
                    Toast.makeText(tienda.this,"Puntos insuficientes",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Salir").setMessage("Desea salir de la tienda?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                ).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent j = new Intent(tienda.this,MapsActivity.class);
                        startActivity(j);
                    }
                });

        builder.show();
    }



}
