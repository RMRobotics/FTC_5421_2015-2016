package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.hardware.DcMotor;

import static java.lang.Math.abs;

public class Motor {

    private static final double MAX_POWER = 1.0;
    private static final double MIN_POWER = 0.1;

    private DcMotor parent;
    private DcMotor.Direction defaultDirection;
    private double minPower;
    private double maxPower;
    private double desiredPower;
    private double currentPower;
    private double interval;

    public Motor(DcMotor dc, DcMotor.Direction d, double min, double max) {
        parent = dc;
        defaultDirection = d;
        minPower = min;
        maxPower = max;
    } //Todo add string to send in DbgLog confirming motor settings once configured

    public void setDesiredPower(double d) {
        desiredPower = d;
    }

    public void updateCurrentPower() {
        /**
         * let it be zero if it needs to be
         * absolute value and switch direction if needed
         * clip to be within range
         *
         */
        double absDesPower = abs(desiredPower);
        if (absDesPower == 0.0 || absDesPower < minPower) {
            currentPower = desiredPower;
        } else {
            if (desiredPower < 0.0) {
                if (defaultDirection == DcMotor.Direction.FORWARD) {
                    parent.setDirection(DcMotor.Direction.REVERSE);
                } else if (defaultDirection == DcMotor.Direction.REVERSE) {
                    parent.setDirection(DcMotor.Direction.FORWARD);
                }
            } else {
                if (defaultDirection == DcMotor.Direction.FORWARD) {
                    parent.setDirection(DcMotor.Direction.FORWARD);
                } else if (defaultDirection == DcMotor.Direction.REVERSE) {
                    parent.setDirection(DcMotor.Direction.REVERSE);
                }
            }
            desiredPower = absDesPower;
            if (desiredPower > maxPower) {
                desiredPower = maxPower;
            }
            currentPower = desiredPower;
        }

        /*if(desiredPower<0){
            if(defaultDirection == DcMotor.Direction.FORWARD){
                defaultDirection = DcMotor.Direction.REVERSE;
            }else if (defaultDirection == DcMotor.Direction.REVERSE){
                defaultDirection = DcMotor.Direction.FORWARD;
            }
            desiredPower = Math.abs(desiredPower);
            minPower = Math.abs(minPower);
            maxPower=Math.abs(maxPower);
        }
        desiredPower = Range.clip(desiredPower, minPower, maxPower);

        currentPower =  desiredPower;*/

    }

    public void setCurrentPower() {
        interval = abs((desiredPower - currentPower)/3);//I made kinda like "steps" for acceleration, so it will always accelerate in 10 steps

        if (desiredPower > currentPower) {
            while (currentPower < desiredPower) {
                parent.setPower(currentPower);
                currentPower += interval;
            }
        } else if (desiredPower < currentPower) {
            while (currentPower > desiredPower) {
                parent.setPower(currentPower);
                currentPower -= interval;
            }
        } else {
            parent.setPower(currentPower);
        }
    }
}