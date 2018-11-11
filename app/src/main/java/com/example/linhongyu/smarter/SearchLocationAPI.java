package com.example.linhongyu.smarter;

/**
 * Created by linhongyu on 27/4/18.
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchLocationAPI {

    private static final String API_KEY  = "AIzaSyDdszEDkLOXrfFrbqtFJFUgmqJlzASsuXA";

    public static String search(String address) {
        address = address.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address +
                    "&key="+ API_KEY);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }


    public static String getGeo(String result, String type){
        String geoLat = null;
        String geoLon = null;
        String geo;
        try{

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            geo =jsonArray.getJSONObject(0).getString("geometry");
            JSONObject GEO = new JSONObject(geo);
            JSONObject location =  GEO.getJSONObject("location");
            geoLat = location.getString("lat");
            geoLon = location.getString("lng");


        }catch (Exception e){
            e.printStackTrace();
        }
        if (type.equals("geoLat")) {
            return geoLat;
        }
        else
            return geoLon;
    }

}
