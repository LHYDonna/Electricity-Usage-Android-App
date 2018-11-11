package com.example.linhongyu.smarter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linhongyu on 23/4/18.
 */


public class MainFragment extends Fragment {

    private View vMain;
    private TextView tvWelcome,tvDate,tvTemp,tvUsage;
    private Button btn;
    private Calendar date;
    public static String tvlat;
    public static String tvlon;
    private static int temperature;
    private static int record;
    private ImageView imageView;
    private DBManager dbManager;
    private UsageGenerater usageGenerater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        tvWelcome = (TextView) vMain.findViewById(R.id.tv_welcome);
        tvDate = (TextView) vMain.findViewById(R.id.tv_currentDate);
        tvTemp =(TextView) vMain.findViewById(R.id.tv_temperature);
        tvUsage = (TextView) vMain.findViewById(R.id.tv_realUsage);
        btn = (Button) vMain.findViewById(R.id.btn_upload);
        imageView = (ImageView) vMain.findViewById(R.id.imageView);
        dbManager = new DBManager(this.getActivity());

        date = Calendar.getInstance();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

        tvWelcome.setText(login.resinfo.getFirstname() + ", Welcome");
        tvDate.setText(df.format(date.getTime()));
        tvTemp.setText(login.resinfo.getAddress());

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String geo = SearchLocationAPI.search(login.resinfo.getAddress());
                tvlat = SearchLocationAPI.getGeo(geo, "geoLat");
                tvlon = SearchLocationAPI.getGeo(geo,"geoLon");
                return  WeatherAPI.search(tvlat, tvlon);
            }
            @Override
            protected  void onPostExecute(String result) {
                temperature = (int) (Double.valueOf(WeatherAPI.getTemperature(result)) - 273.15);
                tvTemp.setText("Temperature is: " + String.valueOf(temperature) + "℃" );
                usageGenerater = new UsageGenerater(temperature);
                tvUsage.setText("Real Usage is: " + String.valueOf(usageGenerater.getTotalUsage()));
                if (usageGenerater.getTotalUsage()> 1.5){
                    imageView.setImageResource(R.drawable.bad);
                }
                else{
                    imageView.setImageResource(R.drawable.good);
                }
            }
        }.execute();

        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                record = RestClient.getRecordOfElecusage();
                return record;
            }
            protected  void onPostExecute(Integer count) {
                dbManager.open();
                dbManager.insertUsage(count + 1,login.resinfo.getResid(),formatter.format(date.getTime()),
                        date.get(Calendar.HOUR_OF_DAY),usageGenerater.getFridge(),usageGenerater.getAir(temperature),usageGenerater.getWashing(),temperature);
                dbManager.close();
            }
        }.execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        Elecusage elecusage = new Elecusage(record + 1,login.resinfo,date.getTime(),
                                date.get(Calendar.HOUR_OF_DAY),usageGenerater.getFridge(),usageGenerater.getAir(temperature),usageGenerater.getWashing(),temperature);
                        RestClient.createUsage(elecusage);
                        dbManager.open();
                        dbManager.deleteAllUsages();
                        dbManager.close();
                        return "Upload Successfully";
                    }
                    protected  void onPostExecute(String result) {
                        btn.setText(result);
                    }
                }.execute();
            }
        });
        return vMain;
    }
}
