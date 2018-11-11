package com.example.linhongyu.smarter;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by linhongyu on 27/4/18.
 */

public class WeatherAPI {
    private static final String API_KEY  = "9cbfa1097f6a306bddc971864b84afe0";

    public static String search(String lat, String lon) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?appid="+ API_KEY+
                    "&lat="+ lat + "&lon=" + lon);
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

    public static String getTemperature(String result){
        String temp;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject1 = jsonObject.getJSONObject("main");
            temp = jsonObject1.getString("temp");
        }catch (Exception e) {
            e.printStackTrace();
            temp = "not found!";
        }
        return temp;
    }
}
