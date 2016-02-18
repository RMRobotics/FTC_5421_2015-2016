package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.rmrobotics.library.core.RMAutoMode;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;
import com.rmrobotics.library.util.StateType;

import java.text.DecimalFormat;

/**
 * Created by Simon on 2/17/2016.
 */
public class Auto5421 extends RMAutoMode {

    private final int WAIT = 5421;

    int prevState;
    int state = 1;
    StateType prevStateType;

    Motor driveLeft;
    Motor driveRight;
    Motor extendLeft;
    Motor extendRight;
    rServo climbers;

    DecimalFormat df = new DecimalFormat("#.##");

    private final String CONFIGURATION_PATH = "res/5421robot_cleanUpImprovement.JSON";

    @Override
    public void init() {
        super.init();
        for (Motor m : motorMap.values()) {
            m.runUsingEncoders();
        }
        driveLeft = motorMap.get("driveLeft");
        driveRight = motorMap.get("driveRight");
        extendLeft = motorMap.get("extendLeft");
        extendRight = motorMap.get("extendRight");
    }

    @Override
    protected void calculate() {
        switch (state) {
            case 1:
                setDriveTarget(1000);
                setDrivePower(1.0);
                updateState(StateType.ENCODER_DRIVE);
                break;
            case 2:
                break;
            case WAIT:
                switch (prevStateType) {
                    case ENCODER_DRIVE:
                        if (driveDone()) {
                            updateStateWait();
                        }
                        break;
                    case ENCODER_EXTEND:
                        if 
                }
        }
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    private void addTelemetry() {
        telemetry.addData("L1-L2-R1-R2-H-B-LF-RF-C", df.format(motorMap.get("DriveLeftOne").getPower()) + "-"
                + df.format(motorMap.get("DriveLeftTwo").getPower()) + "-" + df.format(motorMap.get("DriveRightOne").getPower()) + "-"
                + df.format(motorMap.get("DriveRightTwo").getPower()) + "-" + df.format(motorMap.get("Harvester").getPower()) + "-"
                + df.format(motorMap.get("Bucket").getPower()) + "-" + df.format(servoMap.get("BucketLeft").getPosition()) + "-"
                + df.format(servoMap.get("BucketRight")) + "-" + df.format(servoMap.get("Climbers").getPosition()));
    }

    private void setDriveTarget(int target) {
        driveLeft.setTargetPosition(target);
        driveRight.setTargetPosition(target);
    }

    private void setDriveTarget(int targetLeft, int targetRight) {
        driveLeft.setTargetPosition(targetLeft);
        driveRight.setTargetPosition(targetRight);
    }

    private void setDrivePower(double power) {
        driveLeft.setDesiredPower(power);
        driveRight.setDesiredPower(power);
    }

    private void setDrivePower(double powerLeft, double powerRight) {
        driveLeft.setDesiredPower(powerLeft);
        driveRight.setDesiredPower(powerRight);
    }

    private void updateState(StateType type) {
        prevState = state;
        prevStateType = type;
        state = WAIT;
    }

    private void updateStateWait() {
        state = prevState + 1;
        prevState = WAIT;
        prevStateType = StateType.WAIT;
    }

    private boolean driveDone() {
        if (Math.abs(driveLeft.getCurrentPosition() - driveLeft.getTargetPosition()) < 100) {
            if (Math.abs(driveLeft.getCurrentPosition() - driveLeft.getTargetPosition()) < 10) {
                driveLeft.setDesiredPower(0);
            } else if (driveLeft.getCurrentPosition() < driveLeft.getTargetPosition()) {
                driveLeft.setDesiredPower(0.3);
            } else {
                driveLeft.setDesiredPower(-0.3);
            }
        }
        if (Math.abs(driveRight.getTargetPosition() - driveRight.getTargetPosition()) < 10) {
            if (Math.abs(driveRight.getCurrentPosition() - driveRight.getTargetPosition()) < 10) {
                driveRight.setDesiredPower(0);
            } else if (driveRight.getCurrentPosition() < driveRight.getTargetPosition()) {
                driveRight.setDesiredPower(0.3);
            } else {
                driveRight.setDesiredPower(-0.3);
            }
        }
        if (driveLeft.getPower() < 0.1 && driveRight.getPower() < 0.1) {
            return true;
        } else {
            return false;
        }
    }
}