package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RM Robotics on 11/23/2015.
 */

public class rServo{

    private static final double MAX_POS = 1.0;
    private static final double MIN_POS = 0.1;

    private Servo parent;
    private Servo.Direction defaultDirection;
    private double minPos;
    private double maxPos;
    private double desiredPos;
    private double currentPos;
    private boolean isContinuous;

    public rServo(Servo s, boolean x, double min, double max){
        parent = s;
        isContinuous = x;
        minPos = min;
        maxPos = max;
    }

    public void setDesiredPos(double d){
        desiredPos = d;
    }

    public void updateCurrentPos(double x) {
        desiredPos = Range.clip(desiredPos, minPos, maxPos);
        currentPos = desiredPos;
    }

    public void setPos(){parent.setPosition(currentPos)}
}