package com.apps.hinoir.pratico1full;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //--------------------------------------------
    //Atributos
    //--------------------------------------------
    private static final int REQUEST_CODE = 11;
    private GoogleMap mMap;
    private LocationManager manager;
    private LatLng usuario;
    private FloatingActionButton bPreguntaFacil;
    private FloatingActionButton bPreguntaDificil;
    private FloatingActionButton bTienda;
    private Marker markerActual;
    //--------------------------------------------
    //Poligonos
    //--------------------------------------------
    private Polygon polygonBiblio;
    private Polygon polygonM;
    private Polygon polygonL;
    //--------------------------------------------
    //Posiciones
    //--------------------------------------------
    public LatLng B1 = new LatLng(3.341682, -76.530092);
    public LatLng B2 = new LatLng(3.341907, -76.530065);
    public LatLng B3 = new LatLng(3.341912, -76.529802);
    public LatLng B4 = new LatLng(3.341666, -76.529802);

    public LatLng M1 = new LatLng(3.342298, -76.530429);
    public LatLng M2 = new LatLng(3.342368, -76.530419);
    public LatLng M3 = new LatLng(3.342352, -76.530022);
    public LatLng M4 = new LatLng(3.342287, -76.530016);

    public LatLng L1 = new LatLng(3.340922, -76.529474);
    public LatLng L2 = new LatLng(3.341430, -76.529432);
    public LatLng L3 = new LatLng(3.341436, -76.529287);
    public LatLng L4 = new LatLng(3.340916, -76.529303);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        bPreguntaFacil = findViewById(R.id.fab_preguntaFacil);
        bPreguntaDificil = findViewById(R.id.fab_preguntaDificil);
        bTienda = findViewById(R.id.fab_tienda);


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
        usuario = new LatLng(3.341800, -76.529941);
        markerActual=mMap.addMarker(new MarkerOptions().position(usuario).title("Yo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usuario));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usuario,20));
        iniciarPolyg();
        calcularCercano();

        //Agregar un listener de ubicacion
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                markerActual.remove();
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

    public void calcularCercano(){

        if(PolyUtil.containsLocation(usuario,polygonM.getPoints(),true)){
            bPreguntaFacil.show();
            bPreguntaFacil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent j = new Intent(MapsActivity.this,preguntaFacil.class);
                    startActivity(j);
                    }
                });
            }
            else if(PolyUtil.containsLocation(usuario,polygonL.getPoints(),true)){
                bPreguntaDificil.show();
                bPreguntaDificil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent j = new Intent(MapsActivity.this,pregunta.class);
                        startActivity(j);
                    }
                });

            }
            else if(PolyUtil.containsLocation(usuario,polygonBiblio.getPoints(),true)){
                bTienda.show();
                bTienda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent j = new Intent(MapsActivity.this,tienda.class);
                        startActivity(j);
                    }
                });
            }
            else{
                bPreguntaFacil.hide();
                bPreguntaDificil.hide();
                bTienda.hide();
        }

    }

    public void iniciarPolyg(){
        polygonBiblio=mMap.addPolygon(new PolygonOptions().add(B1,B2,B3,B4));
        polygonBiblio.setStrokeColor(Color.BLUE);

        polygonL=mMap.addPolygon(new PolygonOptions().add(L1,L2,L3,L4));
        polygonL.setStrokeColor(Color.GREEN);

        polygonM=mMap.addPolygon(new PolygonOptions().add(M1,M2,M3,M4));
        polygonM.setStrokeColor(Color.RED);

    }

}
