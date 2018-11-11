package com.example.linhongyu.smarter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.exceptions.IconBitmapChangedException;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhongyu on 23/4/18.
 */

public class DisplayMap extends Fragment {

    View vDisplayMap;
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private Spinner spinner;
    private static ArrayList<Rescredential> rescredentials;
    private static ArrayList<Elecusage> elecusages;
    private static ArrayList<LatLng> latLngs;
    private static LatLng Home = new LatLng(Double.valueOf(MainFragment.tvlat),Double.valueOf(MainFragment.tvlon));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        MapboxAccountManager.start(getActivity().getApplicationContext());
        vDisplayMap = inflater.inflate(R.layout.fragment_map, container, false);
        spinner = (Spinner) vDisplayMap.findViewById(R.id.sp_map);
        mMapView = (MapView) vDisplayMap.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        latLngs = new ArrayList<>();
        elecusages = new ArrayList<>();

        new AsyncTask<String, Void, ArrayList<LatLng>>() {

            @Override
            protected ArrayList<LatLng> doInBackground(String... params) {
                rescredentials = (ArrayList<Rescredential>) RestClient.getRescredential();
                ArrayList<String> geos = new ArrayList<>();
                ArrayList<String> tvlats = new ArrayList<>();
                ArrayList<String> tvlons = new ArrayList<>();
                for (int i = 0 ; i < rescredentials.size(); i++){
                    geos.add(SearchLocationAPI.search(rescredentials.get(i).getResid().getAddress()));
                    tvlats.add(SearchLocationAPI.getGeo(geos.get(i), "geoLat"));
                    tvlons.add(SearchLocationAPI.getGeo(geos.get(i),"geoLon"));
                    latLngs.add(new LatLng(Double.valueOf(tvlats.get(i)),Double.valueOf(tvlons.get(i))));
                }
                Log.i("Number Of ","latlng" + latLngs.size());
                return latLngs;
            }

        }.execute();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String msupplier=spinner.getSelectedItem().toString();
                if (msupplier.equalsIgnoreCase("hour")){

                    new AsyncTask<String, Void, ArrayList<Double>>() {

                        @Override
                        protected ArrayList<Double> doInBackground(String... params) {
                            ArrayList<Double> sumList = new ArrayList<>();
                            Log.i("Elecusage","print"+ rescredentials.size());
                            for (int i = 0 ; i < rescredentials.size(); i++){
                                elecusages = (ArrayList<Elecusage>) RestClient.getElecusage(i + 1,"hour");
                                if (elecusages == null)
                                    break;
                                else{
                                    Double sum = Double.valueOf(0);
                                    for (int j = 0; j < elecusages.size();j++){
                                        double total = elecusages.get(j).getConditionerusage()
                                                + elecusages.get(j).getFridgeusage() + elecusages.get(j).getConditionerusage();
                                        sum += total;
                                    }
                                    sumList.add(sum);
                                }
                            }
                            return sumList;
                        }

                        @Override
                        protected void onPostExecute(final ArrayList<Double> totals) {
                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(MapboxMap mapboxMap) {
                                    mMapboxMap = mapboxMap;
                                    mapboxMap.clear();
                                    mMapboxMap.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(Home, 9));
                                    for (int i = 0 ; i < latLngs.size(); i++) {
                                        addMarker(mMapboxMap, latLngs.get(i),totals.get(i),"hour");
                                    }
                                }
                            });
                        }

                    }.execute();

                }
                if (msupplier.equalsIgnoreCase("day")){

                    new AsyncTask<String, Void, ArrayList<Double>>() {

                        @Override
                        protected ArrayList<Double> doInBackground(String... params) {
                            ArrayList<Double> sumList = new ArrayList<>();
                            Log.i("Elecusage","print"+ rescredentials.size());
                            for (int i = 0 ; i < rescredentials.size(); i++){
                                elecusages = (ArrayList<Elecusage>) RestClient.getElecusage(i + 1,"day");
                                if (elecusages == null)
                                    break;
                                else{
                                    Double sum = Double.valueOf(0);
                                    for (int j = 0; j < elecusages.size();j++){
                                        double total = elecusages.get(j).getConditionerusage()
                                                + elecusages.get(j).getFridgeusage() + elecusages.get(j).getConditionerusage();
                                        sum += total;
                                    }
                                    sumList.add(sum);
                                }
                            }
                            return sumList;
                        }

                        @Override
                        protected void onPostExecute(final ArrayList<Double> totals) {
                            mMapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(MapboxMap mapboxMap) {
                                    mMapboxMap = mapboxMap;
                                    mapboxMap.clear();
                                    mMapboxMap.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(Home, 9));
                                    for (int i = 0 ; i < latLngs.size(); i++) {
                                        addMarker(mMapboxMap, latLngs.get(i),totals.get(i),"day");
                                    }
                                }
                            });
                        }

                    }.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        return vDisplayMap;
    }

    @Override
    public void onResume()
    {   super.onResume();
        mMapView.onResume(); }
    @Override
    public void onPause()
    {   super.onPause();
        mMapView.onPause(); }
    @Override
    public void onDestroy()
    {   super.onDestroy();
        mMapView.onDestroy(); }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private void addMarker(MapboxMap mapboxMap,LatLng lng,double total,String type) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(lng);
        final Icon iconGreen = IconFactory.getInstance(getActivity()).fromResource(R.drawable.green);
        final Icon iconRed = IconFactory.getInstance(getActivity()).fromResource(R.drawable.red);

        if (lng.equals(Home))
            markerOptions.snippet("You are here");
        else
            markerOptions.snippet("");

        markerOptions.title(String.valueOf(total));
        if (type.equals("day")){
            if (total < 21)
                markerOptions.icon(iconGreen);
            else
                markerOptions.icon(iconRed);
        }
        if (type.equals("hour")){
            if (total < 1.5)
                markerOptions.icon(iconGreen);
            else
                markerOptions.icon(iconRed);
        }
        //markerOptions.snippet("Welcome to San Fran!");
        mapboxMap.addMarker(markerOptions);
    }

}

