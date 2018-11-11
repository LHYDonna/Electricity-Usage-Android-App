package com.example.linhongyu.smarter;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineFragment extends Fragment {

    View vLine;
    Spinner spinner;
    LineChart chart;

    public LineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vLine = inflater.inflate(R.layout.line_fragment, container, false);
        spinner = (Spinner) vLine.findViewById(R.id.select_spinner);
        chart =(LineChart) vLine.findViewById(R.id.chart_line);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier=spinner.getSelectedItem().toString();
                if (msupplier.equalsIgnoreCase("hour")){
                    new AsyncTask<String, Void, List<Elecusage>>() {
                        @Override
                        protected List<Elecusage> doInBackground(String... params) {

                            return RestClient.getElecusage(login.resinfo.getResid(),"hour");
                        }
                        @Override
                        protected void onPostExecute(List<Elecusage> newElecusageList) {

                            List<Entry> entries = new ArrayList<Entry>();

                            ArrayList<String> xAxis = new ArrayList<>();
                            ArrayList<Entry> yAxisTemp = new ArrayList<>();
                            ArrayList<Entry> yAxisUsage = new ArrayList<>();

                            for (int i = 0; i < newElecusageList.size(); i++){
                                float temp = (float) newElecusageList.get(i).getTemperature();
                                double usage = newElecusageList.get(i).getConditionerusage()
                                        + newElecusageList.get(i).getFridgeusage()
                                        + newElecusageList.get(i).getWashingusage();
                                yAxisTemp.add(new Entry(i,temp));
                                yAxisUsage.add(new Entry(i,(float) usage));
                                xAxis.add(i,String.valueOf(newElecusageList.get(i).getUsehour()));
                                Log.i("Temp: ","Usage: " + temp + "/" + usage);
                                }

                            final String[] xaxes = new String[xAxis.size()];
                            for (int i=0; i<xAxis.size(); i++){
                                xaxes[i] = xAxis.get(i).toString();
                            }
                            MyXFormatter formatter = new MyXFormatter(xaxes);

                            if (spinner.getSelectedItem().toString().equalsIgnoreCase("day")){
                                Log.d("Selection2","tt" + spinner.getSelectedItem().toString());

                            }

                            LineDataSet setTemp,setUsage;
                            setTemp = new LineDataSet(yAxisTemp,"Temperature");
                            setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
                            setTemp.setColor(Color.RED);

                            setUsage = new LineDataSet(yAxisUsage,"Usage");
                            setUsage.setAxisDependency(YAxis.AxisDependency.RIGHT);
                            setUsage.setColor(Color.BLUE);

                            LineData data = new LineData(setTemp,setUsage);

                            XAxis xAxisFromChart = chart.getXAxis();
                            xAxisFromChart.setDrawAxisLine(false);
                            xAxisFromChart.setLabelCount(xaxes.length);
                            xAxisFromChart.setValueFormatter(formatter);
                            // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
                            xAxisFromChart.setGranularity(1f);
                            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                            chart.setData(data);
                            chart.animateY(1000);

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
                            int count = 0;
                            double sumTemp = 0;

                            ArrayList<Float> yAxisUsage = new ArrayList<>();
                            ArrayList<Float> yAxisTemp = new ArrayList<>();

                            ArrayList<Entry> yTemp = new ArrayList<>();
                            ArrayList<Entry> yUsage = new ArrayList<>();
                            ArrayList<String> xDate = new ArrayList<>();

                            for (int i = 0; i < results.size(); i++){
                                double usage = results.get(i).getConditionerusage() + results.get(i).getFridgeusage()
                                        + results.get(i).getWashingusage();
                                double temp = results.get(i).getTemperature();
                                if (formatter.format(results.get(i).getUsedate()).equals(date)){
                                    sum += usage;
                                    count += 1;
                                    sumTemp += temp;
                                }
                                if (!formatter.format(results.get(i).getUsedate()).equals(date)) {
                                    yAxisUsage.add(Float.valueOf((float)sum));
                                    yAxisTemp.add(Float.valueOf((float)sumTemp/count));
                                    xDate.add(formatter.format(results.get(i-1).getUsedate()));
                                    sum = usage;
                                    count = 1;
                                    sumTemp = temp;
                                    date = formatter.format(results.get(i).getUsedate());
                                }
                                if (i == results.size() - 1){
                                    yAxisUsage.add(Float.valueOf((float)sum));
                                    yAxisTemp.add(Float.valueOf((float)sumTemp/count));
                                    xDate.add(formatter.format(results.get(i).getUsedate()));
                                }
                            }

                            final String[] xaxes = new String[xDate.size()];
                            for (int i = 0; i < xDate.size(); i++){
                                xaxes[i] = xDate.get(i).toString();
                            }
                            MyXFormatter formatter = new MyXFormatter(xaxes);

                            for (int i = 0; i < yAxisUsage.size(); i++){
                                yUsage.add(new Entry(i,yAxisUsage.get(i)));
                                yTemp.add(new Entry(i,yAxisTemp.get(i)));
                            }

                            LineDataSet setTemp,setUsage;
                            setTemp = new LineDataSet(yTemp,"Temperature");
                            setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
                            setTemp.setColor(Color.RED);

                            setUsage = new LineDataSet(yUsage,"Usage");
                            setUsage.setAxisDependency(YAxis.AxisDependency.RIGHT);
                            setUsage.setColor(Color.BLUE);

                            LineData data = new LineData(setTemp,setUsage);

                            XAxis xAxisFromChart = chart.getXAxis();
                            xAxisFromChart.setDrawAxisLine(false);
                            xAxisFromChart.setLabelCount(xaxes.length);
                            xAxisFromChart.setValueFormatter(formatter);
                            xAxisFromChart.setGranularity(1f);
                            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                            chart.setData(data);
                            chart.animateY(1000);
                        }
                    }.execute();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        return vLine;
    }

}
