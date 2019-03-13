package com.apps.hinoir.pratico1full;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private static final int REQUEST_CODE = 11;
    private GoogleMap mMap;
    private LocationManager manager;
    private LatLng usuario;
    private List<MarkerOptions> markerOptions;
    private FloatingActionButton preguntaFacil;
    private FloatingActionButton preguntaDificil;
    private FloatingActionButton tienda;
    private Marker markerActual;
    private int tPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        markerOptions = new ArrayList<>();
        preguntaFacil = findViewById(R.id.fab_preguntaFacil);
        preguntaDificil = findViewById(R.id.fab_preguntaDificil);
        tienda = findViewById(R.id.fab_tienda);
        tPuntos=0;

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_CODE);

        // Add a marker in Icesi and move the camera
        usuario = new LatLng(3.341683, -76.530434);
        mMap.addMarker(new MarkerOptions().position(usuario).title("Yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usuario));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usuario,20));

        MarkerOptions options = new MarkerOptions().title("Dificil")
                .position(new LatLng(3.341200,-76.529392));
        markerOptions.add(options);
        mMap.addMarker(markerOptions.get(markerOptions.size()-1));

        options = new MarkerOptions().title("Facil")
                .position(new LatLng(3.342354,-76.530160));
        markerOptions.add(options);
        mMap.addMarker(markerOptions.get(markerOptions.size()-1));

        options = new MarkerOptions().title("Tienda")
                .position(new LatLng(3.341781,-76.529956));
        markerOptions.add(options);
        mMap.addMarker(markerOptions.get(markerOptions.size()-1));


        //Agregar un listener de ubicacion
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                for(int i=0;i<markerOptions.size();i++){
                    mMap.addMarker(markerOptions.get(i));
                }
                usuario=new LatLng(location.getLatitude(),location.getLongitude());
                markerActual =  mMap.addMarker(new MarkerOptions().position(usuario).title("Yo"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(usuario));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usuario,20));
                calcularCercano();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
    }

    @SuppressLint("RestrictedApi")
    public void calcularCercano(){
        Location act = new Location("actual");
        act.setLatitude(usuario.latitude);
        act.setLongitude(usuario.longitude);
        float referencia = 100000;
        int indice = 0;
        for(int i=0;i<markerOptions.size();i++){
            Location lc = new Location("n"+i);
            lc.setLatitude(markerOptions.get(i).getPosition().latitude);
            lc.setLongitude(markerOptions.get(i).getPosition().longitude);
            float dist = act.distanceTo(lc);
            if(dist<=referencia){
                referencia=dist;
                indice=i;
            }
        }
        float refCer = 100;
        if(referencia<refCer) {
            //cercano.setText("El lugar mas cercano es: " + markerOptions.get(indice).getTitle() + " a " + referencia + " metros");
            if(markerOptions.get(indice).getTitle()=="Facil"){
                preguntaFacil.show();
                preguntaFacil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent j = new Intent(MapsActivity.this,preguntaFacil.class);
                        startActivity(j);
                    }
                });
            }
            else if(markerOptions.get(indice).getTitle()=="Dificil"){
                preguntaDificil.show();
                preguntaDificil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent j = new Intent(MapsActivity.this,pregunta.class);
                        startActivity(j);
                    }
                });

            }
            else if(markerOptions.get(indice).getTitle()=="Tienda"){
                tienda.show();
                tienda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent datos = getIntent();
                        tPuntos+=datos.getExtras().getInt("puntosF");
                        datos.getExtras().remove("puntosF");
                        tPuntos+=datos.getExtras().getInt("puntosD");
                        datos.getExtras().remove("puntosD");
                        tPuntos+=datos.getExtras().getInt("puntosT");
                        Intent j = new Intent(MapsActivity.this,tienda.class);
                        j.putExtra("totalPuntos",tPuntos);
                        startActivity(j);
                    }
                });

            }
            else{
                preguntaFacil.hide();
                preguntaDificil.hide();
                tienda.hide();
            }
        }

    }

}
