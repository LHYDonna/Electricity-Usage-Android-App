package com.example.linhongyu.smarter;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarFragment extends Fragment {

    View vBarChart;
    BarChart barChart;
    Spinner spinner;

    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vBarChart = inflater.inflate(R.layout.bar_fragment, container, false);
        barChart = (BarChart) vBarChart.findViewById(R.id.chart_bar);
        spinner = (Spinner) vBarChart.findViewById(R.id.select_spinner);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");

        int id = login.resinfo.getResid();
        Log.e("Selected item : ",String.valueOf(id));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier=spinner.getSelectedItem().toString();
                if (msupplier.equalsIgnoreCase("hour")){
                    new AsyncTask<String, Void, ArrayList<Elecusage>>() {
                        @Override
                        protected ArrayList<Elecusage> doInBackground(String... params) {
                            return (ArrayList<Elecusage>) RestClient.getElecusage(login.resinfo.getResid(),"hour");
                        }
                        @Override
                        protected  void onPostExecute(ArrayList<Elecusage> results) {
                            List<BarEntry> entries = new ArrayList<>();
                            ArrayList<String> xDate = new ArrayList<>();
                            for (int i = 0; i < results.size(); i++){
                                double usage = results.get(i).getConditionerusage() + results.get(i).getFridgeusage()
                                        + results.get(i).getWashingusage();
                                entries.add(new BarEntry(results.get(i).getUsehour(), (float)usage));
                                xDate.add(String.valueOf(results.get(i).getUsehour()));
                            }

                            final String[] xaxes = new String[xDate.size()];
                            for (int i=0; i<xDate.size(); i++){
                                xaxes[i] = xDate.get(i).toString();
                            }
                            MyXFormatter formatter = new MyXFormatter(xaxes);

                            BarDataSet set = new BarDataSet(entries, "Hourly Usage");
                            BarData data = new BarData(set);
                            data.setBarWidth(0.9f); // set custom bar width

                            XAxis xAxisFromChart = barChart.getXAxis();
                            xAxisFromChart.setDrawAxisLine(false);
                            xAxisFromChart.setLabelCount(xaxes.length);
                            xAxisFromChart.setValueFormatter(formatter);
                            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                            barChart.setData(data);
                            barChart.setFitBars(true);// make the x-axis fit exactly all bars
                            barChart.animateY(1000);
                            barChart.invalidate(); // refresh
                        }
                    }.execute();
                }
                if (msupplier.equalsIgnoreCase("day")){
                    new AsyncTask<String, Void, ArrayList<Elecusage>>() {
                        @Override
                        protected ArrayList<Elecusage> doInBackground(String... params) {
                            return (ArrayList<Elecusage>) RestClient.getElecusage(login.resinfo.getResid(),"day");
                        }
                        @Override
                        protected  void onPostExecute(ArrayList<Elecusage> results) {
                            List<BarEntry> entries = new ArrayList<>();
                            String date = formatter.format(results.get(0).getUsedate());
                            double sum = 0;

                            ArrayList<Float> yData = new ArrayList<>();
                            ArrayList<String> xDate = new ArrayList<>();

                            for (int i = 0; i < results.size(); i++){
                                double usage = results.get(i).getConditionerusage() + results.get(i).getFridgeusage()
                                        + results.get(i).getWashingusage();
                                if (formatter.format(results.get(i).getUsedate()).equals(date))
                                    sum += usage;
                                if (!formatter.format(results.get(i).getUsedate()).equals(date)) {
                                    yData.add(Float.valueOf((float)sum));
                                    xDate.add(formatter.format(results.get(i-1).getUsedate()));
                                    sum = usage;
                                    date = formatter.format(results.get(i).getUsedate());
                                }
                                if (i == results.size() - 1){
                                    yData.add(Float.valueOf((float)sum));
                                    xDate.add(formatter.format(results.get(i).getUsedate()));
                                }
                            }

                            final String[] xaxes = new String[xDate.size()];
                            for (int i=0; i<xDate.size(); i++){
                                xaxes[i] = xDate.get(i).toString();
                            }
                            MyXFormatter formatter = new MyXFormatter(xaxes);

                            for (int i = 0; i < yData.size(); i++){
                                entries.add(new BarEntry(i+1,yData.get(i)));
                            }


                            BarDataSet set = new BarDataSet(entries, "Daily Usage");
                            BarData data = new BarData(set);
                            XAxis xAxisFromChart = barChart.getXAxis();
                            xAxisFromChart.setDrawAxisLine(false);
                            xAxisFromChart.setLabelCount(xaxes.length);
                            xAxisFromChart.setValueFormatter(formatter);
                            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                            data.setBarWidth(0.9f); // set custom bar width

                            barChart.setData(data);
                            barChart.setFitBars(true); // make the x-axis fit exactly all bars
                            barChart.animateY(1000);
                            barChart.invalidate(); // refresh
                        }
                    }.execute();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        return vBarChart;
    }

}
