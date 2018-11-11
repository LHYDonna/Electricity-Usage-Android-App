package com.example.linhongyu.smarter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by linhongyu on 29/4/18.
 */

public class UsageGenerater {

    private double air;
    private double washing;
    private static boolean washingFlag = false;
    private double fridge;
    private static int hour = getCurrentHour();
    private static int lastAirHour = -1, lastWashingHour = -1;
    private static int airCounter = 0, washingCounter = 0;

    public static short getCurrentHour(){
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        return (short)calendar.get(Calendar.HOUR_OF_DAY);
    }

    public UsageGenerater()
    {
        fridge = 0;
        air = 0;
        washing = 0;
    }

    public UsageGenerater(double temp)
    {
        air = getAir(temp);
        washing = getWashing();
        fridge = getFridge();
    }

    public UsageGenerater(double newAir, double newWashing, double newFridge){

        air = newAir;
        washing = newWashing;
        fridge = newFridge;
    }

    public double getAir(double temp)
    {
        double result = 0;
        if (hour < 23 && hour > 9 && temp > 20 ){
            if (Math.random() * 2 > 1){
                //decide to use
                if (hour==lastAirHour){
                    //update
                    result = Math.random() * 4 + 1;
                }else{
                    //new data
                    if (airCounter < 10){
                        airCounter++;
                        lastAirHour = hour;
                        result = Math.random() * 4 + 1;
                    }else{
                        result = 0;
                    }

                }
            }else{
                //decide not to use
                result = 0;
            }
        }
        else
            result = 0;
        return result;
    }

    public double getWashing()
    {
        double result = 0;
        if (hour>=6 && hour<=9){
            //use a random to decide use or not
            if (Math.random() * 2 > 1){
                //deicide to use
                if ( washingFlag == false){
                    // not used
                    washingCounter++;
                    washingFlag = true;
                    result = Math.random() * 0.9 + 0.4;
                } else {
                    // already used
                    if (hour == lastWashingHour) {
                        //update hour
                        result = Math.random() * 0.9 + 0.4;
                    } else {
                        //new data
                        if (hour - lastWashingHour > 1)
                            // not continue
                            result = 0;
                        else {
                            //continue
                            if (washingCounter < 3) {
                                //less than 3 hours
                                lastWashingHour = hour;
                                washingCounter++;
                                result = Math.random() * 0.9 + 0.4;
                            } else {
                                result = 0;
                            }
                        }
                    }
                }
            }else{
                //decide not to use
                result = 0;
            }
        }else{
            result = 0;
        }
        return result;
    }

    public double getFridge()
    {
        double result = Math.random() * 0.5 + 0.3;
        return result;
    }

    public double getTotalUsage()
    {
        double total = air + washing + fridge;
        return total;
    }

    public void setAir(double newAir)
    {
        air = newAir;
    }

    public void setWashing(double newWashing)
    {
        washing = newWashing;
    }

    public void setFridge(double newFridge)
    {
        fridge = newFridge;
    }
}
