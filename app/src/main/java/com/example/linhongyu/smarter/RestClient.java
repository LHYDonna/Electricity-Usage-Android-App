package com.example.linhongyu.smarter;

/**
 * Created by linhongyu on 21/4/18.
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URI = "http://10.0.0.15:41072/SmartER/webresources";

    public static void createResinfo(Resinfo resinfo){ //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/smarter.resinfo/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
            String stringInfoJson = gson.toJson(resinfo);
            Log.i("Boolean2","result" + stringInfoJson);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringInfoJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringInfoJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
    }

    public static void createRescredential(Rescredential rescredential){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/smarter.rescredential/";
        try {
            Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
            String stringRescreditialJson = gson.toJson(rescredential);
            Log.i("Boolean3","result" + stringRescreditialJson);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringRescreditialJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringRescreditialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createUsage(Elecusage elecusage){
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/smarter.elecusage/";
        try {
            Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
            String stringRescreditialJson = gson.toJson(elecusage);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringRescreditialJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringRescreditialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String checkLogin(String username, String passwordhash) {
        final String methodPath = "/smarter.rescredential/findByUsernameAndPasswordhash/" + username + "/" + passwordhash;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static List<Rescredential> getResinfo(String username) {
        final String methodPath = "/smarter.rescredential/findByUsername/" + username;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }
        //JSON to Java object, read it from a Json String.
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();

        ArrayList<Rescredential> rescredentialList = new ArrayList<Rescredential>();
        Type listType = new TypeToken<List<Rescredential>>() {}.getType();
        rescredentialList = gson.fromJson(textResult, listType);

        return rescredentialList;
    }

    public static List<Rescredential> getRescredential() {
        final String methodPath = "/smarter.rescredential/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        ArrayList<Rescredential> rescredentialList = new ArrayList<Rescredential>();
        Type listType = new TypeToken<List<Rescredential>>() {}.getType();
        rescredentialList = gson.fromJson(textResult, listType);
        Log.i("Latlngs","tag"+rescredentialList.get(0).getResid().getAddress());
        return rescredentialList;
    }

    public static List<Elecusage> getElecusage(int userid,String selection) {
        final String methodPath = "/smarter.elecusage/findByResidAndSelection/" + userid + "/" + selection;
        //Log.d("result1","test"+methodPath);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }


        //JSON to Java object, read it from a Json String.
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();

        ArrayList<Elecusage> elecusageList = new ArrayList<Elecusage>();
        Type listType = new TypeToken<List<Elecusage>>() {}.getType();
        elecusageList = gson.fromJson(textResult, listType);
        return elecusageList;
    }

    public static List<Elecusage> getElecusageBySelection (String selection) {
        final String methodPath = "/smarter.elecusage/findBySelection/" + selection;
        //Log.d("result1","test"+methodPath);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }


        //JSON to Java object, read it from a Json String.
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();

        ArrayList<Elecusage> elecusageList = new ArrayList<Elecusage>();
        Type listType = new TypeToken<List<Elecusage>>() {}.getType();
        elecusageList = gson.fromJson(textResult, listType);
        return elecusageList;
    }

    public static int getRecordOfElecusage() {
        final String methodPath = "/smarter.elecusage/";
        //Log.d("result1","test"+methodPath);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        ArrayList<Elecusage> elecusageList = new ArrayList<Elecusage>();
        Type listType = new TypeToken<List<Elecusage>>() {}.getType();
        elecusageList = gson.fromJson(textResult, listType);

        return elecusageList.size();
    }

    public static int getRecordOfResinfo() {
        final String methodPath = "/smarter.resinfo/";
        //Log.d("result1","test"+methodPath);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
        ArrayList<Resinfo> resinfo = new ArrayList<Resinfo>();
        Type listType = new TypeToken<List<Resinfo>>() {}.getType();
        resinfo = gson.fromJson(textResult, listType);

        return resinfo.size();
    }

    public static List<Elecusage> getElecusageByid (int id) {
        final String methodPath = "/smarter.elecusage/findByResid/" + id;
        //Log.d("result1","test"+methodPath);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connectionh
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            conn.disconnect();
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();

        ArrayList<Elecusage> elecusageList = new ArrayList<Elecusage>();
        Type listType = new TypeToken<List<Elecusage>>() {}.getType();
        elecusageList = gson.fromJson(textResult, listType);
        return elecusageList;
    }
}
