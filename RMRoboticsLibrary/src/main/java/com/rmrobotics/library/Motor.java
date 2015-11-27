package com.rmrobotics.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class Motor {

    private static final double MAX_POWER = 1.0;
    private static final double MIN_POWER = 0.1;

    private DcMotor parent;
    private DcMotor.Direction defaultDirection;
    private double minPower;
    private double maxPower;
    private double desiredPower;
    private double currentPower;

    //TODO: complete implementation of the MotorState class
    private MotorState ms;

    public Motor (DcMotor dc, DcMotor.Direction d, double min, double max){
        parent = dc;
        defaultDirection = d;
        minPower = min;
        maxPower = max;
    }

    public void setDesiredPower(double d){
        desiredPower = d;
    }

    public void updateCurrentPower(){
        /**
         * let it be zero if it needs to be
         * absolute value and switch direction if needed
         * clip to be within range
         *
         */

        if(desiredPower < 0){

        }else{

        }
        desiredPower = Range.clip(desiredPower, minPower, maxPower);

        currentPower =  desiredPower;

    }



}