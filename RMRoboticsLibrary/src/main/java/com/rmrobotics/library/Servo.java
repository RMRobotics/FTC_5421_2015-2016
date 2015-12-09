package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RM Robotics on 11/23/2015.
 */

public class rServo{

    private static final double MAX_POSITION = 1.0;
    private static final double MIN_POSITION = 0.1;

    private Servo parent;
    private Servo.Direction defaultDirection;
    private double minPosition;
    private double maxPosition;
    private double desiredPosition;
    private double currentPosition;
    private boolean isContinuous;

    public rServo(Servo s, Servo.Direction x, double min, double max, boolean cont){
        parent = s;
        defaultDirection = x;
        isContinuous = cont;
        minPosition = min;
        maxPosition = max;
    }

    public void setDesiredPosition(double d){
        desiredPosition = d;
    }

    public void updateCurrentPosition(){
        desiredPosition = Range.clip(desiredPosition, minPosition, maxPosition);
        currentPosition = desiredPosition;
    }

    public void setPosition(){parent.setPosition(currentPosition);}

    public double getPosition(){return parent.position();}
}