package com.example.avaliacao1.pacote;

import android.content.Context;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.avaliacao1.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationResult;



public class GPS {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private Context context;

    public GPS(Context context) {
        this.context = context;
        fusedClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);// atualizacao do GPS de 3s

    }

    public void inicializarGPS(Caminhao caminhao) {
        if (hasLocationPermission()) {
            atualizacaoGPS(caminhao);
            startLocationUpdates();
        } else requestLocationPermission();
    }

    public void requestLocationPermission() {
        if (context instanceof MainActivity) {
            ActivityCompat.requestPermissions((MainActivity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedClient.removeLocationUpdates(locationCallback);
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
            return permissionCheck == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void atualizacaoGPS(Caminhao caminhao){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // localização atual
                Location location = locationResult.getLastLocation();
                Log.d("entrei","entreiaqui");
                if (location != null) {
                    // Faça algo com a localização
                    Log.d("testando latitude ", String.valueOf(location.getLatitude())+ String.valueOf(location.getLongitude()));
                    caminhao.getLocalizacao().setLat(location.getLatitude());
                    caminhao.getLocalizacao().setLog(location.getLongitude());
                    caminhao.setVelocidade(location.getSpeed());
                    Log.d("latidudeamor", String.valueOf(caminhao.getLocalizacao().getLat()) + String.valueOf(caminhao.getLocalizacao().getLog()));
                    ((MainActivity) context).atualizarTextos();
                }
            }
        };
    }
}


