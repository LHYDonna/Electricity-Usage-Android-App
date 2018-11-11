package com.example.linhongyu.smarter;

import java.util.Date;

/**
 * Created by linhongyu on 26/4/18.
 */

public class Elecusage {

    private Integer usageid;
    private Date usedate;
    private int usehour;
    private double fridgeusage;
    private double conditionerusage;
    private double washingusage;
    private double temperature;
    private Resinfo resid;

    public Elecusage() {
    }

    public Elecusage(Integer usageid) {
        this.usageid = usageid;
    }

    public Elecusage(Integer usageid, Resinfo resid, Date usedate, int usehour,
                     double fridgeusage, double conditionerusage,double washingusage, double temperature) {
        this.usageid = usageid;
        this.resid = resid;
        this.usedate = usedate;
        this.usehour = usehour;
        this.fridgeusage = fridgeusage;
        this.conditionerusage = conditionerusage;
        this.washingusage = washingusage;
        this.temperature = temperature;
    }

    public Integer getUsageid() {
        return usageid;
    }

    public void setUsageid(Integer usageid) {
        this.usageid = usageid;
    }

    public Date getUsedate() {
        return usedate;
    }

    public void setUsedate(Date usedate) {
        this.usedate = usedate;
    }

    public int getUsehour() {
        return usehour;
    }

    public void setUsehour(int usehour) {
        this.usehour = usehour;
    }

    public double getFridgeusage() {
        return fridgeusage;
    }

    public void setFridgeusage(double fridgeusage) {
        this.fridgeusage = fridgeusage;
    }

    public double getConditionerusage() {
        return conditionerusage;
    }

    public void setConditionerusage(double conditionerusage) {
        this.conditionerusage = conditionerusage;
    }

    public double getWashingusage() {
        return washingusage;
    }

    public void setWashingusage(double washingusage) {
        this.washingusage = washingusage;
    }

    public double getTemperature() {
        return temperature ;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Resinfo getResid() {
        return resid;
    }

    public void setResid(Resinfo resid) {
        this.resid = resid;
    }
}
