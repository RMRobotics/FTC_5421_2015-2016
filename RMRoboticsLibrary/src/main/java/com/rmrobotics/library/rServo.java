package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by RM Robotics on 11/23/2015.
 */

public class rServo {

    private boolean isContinuous;
    private double minValue;
    private double maxValue;
    private double power;

    public rServo(boolean x){
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
        x = Range.clip(x, minValue, maxValue);
        power = x;
        speedCheck();
        setPosition(power);
    }

    private void speedCheck(){
        if(power > maxValue){
            power = maxValue;
        } else if(power < minValue){
            power = minValue;
        }
    }

}