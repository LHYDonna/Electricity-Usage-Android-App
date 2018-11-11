package com.example.linhongyu.smarter;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PieFragment extends Fragment {

    View vPie;
    int day,month,year;
    private Calendar currentCalender;
    private PieChart pieChart;
    private Button btn;
    private ArrayList<Elecusage> elecusages;

    public PieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vPie = inflater.inflate(R.layout.pie_fragment, container, false);
        pieChart = (PieChart) vPie.findViewById(R.id.pie_chart);
        final TextView tvDate = (TextView) vPie.findViewById(R.id.tv_time_picker);
        btn = (Button) vPie.findViewById(R.id.btn_pieChart);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");

        currentCalender = Calendar.getInstance();

        day = currentCalender.get(Calendar.DAY_OF_MONTH);
        month = currentCalender.get(Calendar.MONTH);
        year = currentCalender.get(Calendar.YEAR);

        month = month+1;

        tvDate.setText(day + "-" + month + "-" + year);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(vPie.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        if (monthOfYear < 10)
                            tvDate.setText(dayOfMonth + "-0" + monthOfYear + "-" + year);
                        else
                            tvDate.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<String, Void, ArrayList<Elecusage>>() {
                    @Override
                    protected ArrayList<Elecusage> doInBackground(String... params) {
                        elecusages = (ArrayList<Elecusage>) RestClient.getElecusageByid(login.resinfo.getResid());
                        return  elecusages;
                    }
                    @Override
                    protected  void onPostExecute(ArrayList<Elecusage> results) {
                        double air = 0;
                        double washing = 0;
                        double fridge = 0;
                        for (int i = 0; i < results.size(); i++){
                                if (formatter.format(results.get(i).getUsedate()).equals(tvDate.getText())){
                                    air += results.get(i).getConditionerusage();
                                    washing += results.get(i).getWashingusage();
                                    fridge += results.get(i).getFridgeusage();
                                }
                            }

                        float[] yData = {(float) air,(float) washing,(float) fridge};
                        String[] xData = {"Airconditioner","WashingMachine","Fridge"};

                        ArrayList<PieEntry> yEntries = new ArrayList<>();

                        for (int i = 0; i < yData.length; i++){
                            yEntries.add(new PieEntry(yData[i],xData[i]));
                        }

                        PieDataSet set = new PieDataSet(yEntries, "Electricity Usage");
                        set.setSliceSpace(5);
                        set.setColors(ColorTemplate.COLORFUL_COLORS);
                        Legend legend = pieChart.getLegend();
                        legend.setForm(Legend.LegendForm.CIRCLE);

                        PieData data = new PieData(set);
                        data.setValueFormatter(new PercentFormatter());

                        pieChart.setData(data);
                        pieChart.setUsePercentValues(true);
                        pieChart.animateY(1000);
                        pieChart.invalidate();

                    }
                }.execute();
            }
        });

        return vPie;
    }

}
