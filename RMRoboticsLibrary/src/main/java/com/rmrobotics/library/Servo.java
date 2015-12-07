package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RM Robotics on 11/23/2015.
 */

public class rServo{

    private static final double MAX_POWER = 1.0;
    private static final double MIN_POWER = 0.1;

    private Servo parent;
    private Servo.Direction defaultDirection;
    private double minPower;
    private double maxPower;
    private double desiredPower;
    private double currentPower;
    private boolean isContinuous;

    public rServo(Servo s, boolean x){
        parent = s;
        isContinuous = x;
        if(!isContinuous){
            maxPower = 0.9;
            minPower = 0.1;
        } else {
            maxPower = 1;
            minPower = 0;
        }
    }

    public void set(double x) {
        desiredPower = x;
        currentPower = speedCheck(desiredPower);
        //TODO feed power to servo
    }

    private double speedCheck(double p){
        p = Range.clip(minPower, maxPower);
    }

}