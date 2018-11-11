package com.example.linhongyu.smarter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.view.View.Y;

/**
 * Created by linhongyu on 23/4/18.
 */

public class DisplayReport extends Fragment {
    View vReport;
//    Spinner spinner;
//    Button btnSelect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
//        home home = (home)this.getActivity();
//        final String username = home.getUsername();
        vReport = inflater.inflate(R.layout.fragment_report, container, false);


        ViewPager viewPager = (ViewPager) vReport.findViewById(R.id.viewpager);
        FragmentManager fragmentManager = getFragmentManager();
        ReportPage reportPage = new ReportPage(fragmentManager);


        reportPage.addFragment(new PieFragment(),"PiChart");
        reportPage.addFragment(new BarFragment(),"Bar Graph");
        reportPage.addFragment(new LineFragment(),"Line Graph");
        viewPager.setAdapter(reportPage);


        TabLayout tabLayout = (TabLayout) vReport.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //vReport = inflater.inflate(R.layout.line_fragment, container, false);
//        spinner = (Spinner) vReport.findViewById(R.id.select_spinner);
//        btnSelect = (Button) vReport.findViewById(R.id.btn_selected);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if (position == 0){
//                    //List<Elecusage> list = RestClient.getElecusage(username,"day");
//                }
//
//                if (position == 1){
//                    //RestClient.getElecusage(username,"hour");
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//
//        });


//        btnSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AsyncTask<String, Void, List<Elecusage>>() {
//                    @Override
//                    protected List<Elecusage> doInBackground(String... params) {
//                        String username = params[0];
//                        String spSelection = params[1];
//                        return RestClient.getElecusage(username,spSelection);
//                    }
//                    @Override
//                    protected void onPostExecute(List<Elecusage> newElecusageList) {
//                        LineChart chart =(LineChart) vReport.findViewById(R.id.chart);
//                        List<Entry> entries = new ArrayList<Entry>();
//
//
////                ArrayList<Date> dateCollection = new ArrayList<>();
////                for(int i = 0; i < newElecusageList.size(); i++){
////                    dateCollection.add(newElecusageList.get(i).getUsedate());
////                }
//
//
////                final SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
////                Date maxDate = Collections.max(dateCollection);
//
////                ArrayList<Elecusage> latestElecList = new ArrayList<>();
////                for(int i = 0; i < newElecusageList.size(); i++){
////                    if(datef.format(maxDate).equals(datef.format(newElecusageList.get(i).getUsedate())))
////                        latestElecList.add(newElecusageList.get(i));
////                }
//
//                        ArrayList<String> xAxis = new ArrayList<>();
//                        ArrayList<Entry> yAxisTemp = new ArrayList<>();
//                        ArrayList<Entry> yAxisUsage = new ArrayList<>();
//
//                        for (int i = 0; i < newElecusageList.size(); i++){
//                            float temp = (float) newElecusageList.get(i).getTemperature();
//                            double usage = newElecusageList.get(i).getConditionerusage() + newElecusageList.get(i).getFridgeusage() + newElecusageList.get(i).getWashingusage();
//                            yAxisTemp.add(new Entry(i,temp));
//                            yAxisUsage.add(new Entry(i,(float) usage));
//                            xAxis.add(i,String.valueOf(newElecusageList.get(i).getUsehour()));
//                        }
//
//                        final String[] xaxes = new String[xAxis.size()];
//                        for (int i=0; i<xAxis.size(); i++){
//                            xaxes[i] = xAxis.get(i).toString();
//                        }
//
//                        IAxisValueFormatter formatter = new IAxisValueFormatter() {
//                            @Override
//                            public String getFormattedValue(float value, AxisBase axis) {
//                                return xaxes[(int)value];
//                            }
//                        };
//
//                        LineDataSet setTemp,setUsage;
//                        setTemp = new LineDataSet(yAxisTemp,"Temperature");
//                        setTemp.setAxisDependency(YAxis.AxisDependency.LEFT);
//                        setTemp.setColor(Color.RED);
//
//                        setUsage = new LineDataSet(yAxisUsage,"Usage");
//                        setUsage.setAxisDependency(YAxis.AxisDependency.RIGHT);
//                        setUsage.setColor(Color.BLUE);
//
//                        LineData data = new LineData(setTemp,setUsage);
//
//                        chart.setData(data);
//
//                        XAxis xAxisFromChart = chart.getXAxis();
//                        xAxisFromChart.setDrawAxisLine(true);
//                        xAxisFromChart.setValueFormatter(formatter);
//                        // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
//                        xAxisFromChart.setGranularity(1f);
//                        xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//                    }
//                }.execute(username, spinner.getSelectedItem().toString());
//            }
//        });


        return vReport;
    }
}
