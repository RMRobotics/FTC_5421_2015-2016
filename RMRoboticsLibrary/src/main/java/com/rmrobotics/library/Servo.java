package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RM Robotics on 11/23/2015.
 */

public class rServo extends Servo {

    private double minPower;
    private double maxPower;
    private double desiredPower;
    private double currentPower;
    private boolean isContinuous;

    public rServo(boolean x) {
        isContinuous = x;
        if (!isContinuous) {
            maxPower = 0.9;
            minPower = 0.1;
        } else {
            maxPower = 1;
            minPower = 0.1;
        }
    }

    public void set(double x) {
        desiredPower = x;
        currentPower = Range.clip(desiredPower, minPower, maxPower);
        setPosition(currentPower);
    }
}