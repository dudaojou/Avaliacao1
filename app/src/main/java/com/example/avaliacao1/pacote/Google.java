package com.example.avaliacao1.pacote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.avaliacao1.MainActivity;
import com.example.avaliacao1.rec.Reconciliacao;
import com.example.avaliacao1.rec.Reconciliation;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.dom.DOMLocator;

public class Google extends AsyncTask<Object, String, String> {

    private static final String GOOGLE_KEY = "AIzaSyA5ru77oglQe-IObSSQ1fQY61Nxk5QIuWw" ;
    private double latitude;
    private double longitude;
    private double latitudeFinal;
    private double longitudeFinal;
    private Context context;

    public Google(double latitude, double longitude, double latitudeFinal, double longitudeFinal, Context context) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeFinal = latitudeFinal;
        this.longitudeFinal = longitudeFinal;
        this.context = context;
    }

    private String getUrl()
    {
        String googleUrl = "https://maps.googleapis.com/maps/api/directions/json?"+
                "origin="+latitude+","+longitude+
                "&destination="+latitudeFinal+","+longitudeFinal+
                "&key="+GOOGLE_KEY;

        return googleUrl.toString();
    }

    public String readUrl() throws IOException {
        StringBuilder data = new StringBuilder();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            Log.d("downloadUrl", data.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Log.d("data download", data.toString());
        return data.toString();
    }


    @Override
    protected String doInBackground(Object... objects) {
        ArrayList<DistanciaTempo> a1 = (ArrayList<DistanciaTempo>) placedurationspaces();

        if (context instanceof MainActivity){
            ((MainActivity) context).setDistTempo(a1);
        }
        inicializarRec(a1);
        return null;
    }
    public List<DistanciaTempo> placedurationspaces() {
        List<DistanciaTempo> routeSteps = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(readUrl());

            JSONArray steps = json.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONArray("steps");

            JSONArray leg = json.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs");

            int time  = json.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("duration")
                    .getInt("value");

            int duration = json.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("distance")
                    .getInt("value");

            routeSteps.add(new DistanciaTempo(duration,time));

            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                JSONObject startLocation = step.getJSONObject("start_location");
                double lat = startLocation.getDouble("lat");
                double lng = startLocation.getDouble("lng");
                LatLng startPoint = new LatLng(lat, lng);

                int durationValue = step.getJSONObject("duration").getInt("value");
                int distanceValue = step.getJSONObject("distance").getInt("value");

                Log.d("allincluded", "Trecho " + i + " distance: " + distanceValue + " duration " + durationValue);
                routeSteps.add(new DistanciaTempo (durationValue, distanceValue));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return routeSteps;
    }

    public void inicializarRec(ArrayList<DistanciaTempo> dt){
        Reconciliacao rec = new Reconciliacao(dt);
        rec.start();
    }


}