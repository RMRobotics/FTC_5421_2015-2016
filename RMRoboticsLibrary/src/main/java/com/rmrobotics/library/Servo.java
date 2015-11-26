package com.rmrobotics.library;

/**
 * Created by RM Robotics on 11/23/2015.
 */
abstract public class Servo {

    private boolean isContinuous;
    private double minValue;
    private double maxValue;
    private double power;

    public Servo(boolean x){
        isContinuous = x;
        if(!isContinuous){
            maxValue = 0.9;
            minValue = 0.1;
        } else {
            maxValue = 1;
            minValue = 0;
        }
    }

    public void set(double x) {
        power = x;
        speedCheck();
        //TODO: create statement to feed power value to servo
    }

    private int speedCheck(){
        if(power > maxValue){
            power = maxValue;
        } else if(power < minValue){
            power = minValue;
        }
    }

//TODO: create method to scale percentage or RobotC values to current Java values

}
