package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
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
    double sleepTime;

    Motor driveLeft;
    Motor driveRight;
    Motor extendLeft;
    Motor extendRight;
    Motor harvester;
    rServo climbers;
    ElapsedTime runTime;

    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat nf = new DecimalFormat("########");

    private final String CONFIGURATION_PATH = "res/5421robot_cleanUpImprovement.JSON";

    @Override
    public void init() {
        super.init();
        driveLeft = motorMap.get("mL");
        driveRight = motorMap.get("mR");
        extendLeft = motorMap.get("eL");
        extendRight = motorMap.get("eR");
        harvester = motorMap.get("h");
        //climbers = servoMap.get("climbers");
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        addTelemetry();
        runTime.reset();
    }

    @Override
    protected void calculate() {
        for (Motor m : motorMap.values()) {
            m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        switch (state) {
            /*    driveLeft.setTargetPosition(10000);
                driveRight.setTargetPosition(10000);
                setDrivePower(1.0);
                updateState(StateType.ENCODER_DRIVE);
                addTelemetry();
                break;*/
            /*case 2:
                setExtendTarget(1000);
                setExtendPower(1.0);
                updateState(StateType.ENCODER_EXTEND);
                addTelemetry();
                break;*/
            case 1:
                sleepTime = 10000;
                runTime.reset();
                setDrivePower(1.0);
                updateState(StateType.SLEEP);
                addTelemetry();
                break;
            case 2:
                stop();
                break;
            case WAIT:
                switch (prevStateType) {
                    case ENCODER_DRIVE:
                        if (driveDone()) {
                            updateStateWait();
                        }
                        break;
                    case ENCODER_EXTEND:
                        if (extendDone()) {
                            updateStateWait();
                        }
                        break;
                    case SLEEP:
                        if (timeDone()) {
                            updateStateWait();
                        }
                        break;
                }
                addTelemetry();
        }
    }

    @Override
    protected String setConfigurationPath() {
        final String CONFIGURATION_PATH = "{\n" +
                "  \"motors\":[\n" +
                "    {\n" +
                "      \"name\":\"mL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"mR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"eL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_60\"\n" +
                "    },\n" + "{\n" +
                "      \"name\":\"eR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\"\n" +
                "      \"motorType\":\"NVRST_60\"\n" +
                "    },\n" + "{\n" +
                "      \"name\":\"h\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_20\"\n" +
                "    },\n" +
                "  ],\n" +
                "  \"servos\":[\n" +
                "  ],\n" +
                "}";
        return CONFIGURATION_PATH;
    }

    private void addTelemetry() {
        telemetry.addData("S-L-LT-LP-R-RT-RP-LE-LET-LEP-RE-RET-REP-H-C-T", state + "-" + df.format(driveLeft.getPower()) + "-"
                + nf.format(driveLeft.getTargetPosition()) + "-"
                + nf.format(driveLeft.getCurrentPosition()) + "-"
                + df.format(driveRight.getPower()) + "-"
                + nf.format(driveRight.getTargetPosition()) + "-"
                + nf.format(driveRight.getCurrentPosition()) + "-"
                + df.format(extendLeft.getPower()) + "-"
                + nf.format(extendLeft.getTargetPosition()) + "-"
                + nf.format(extendLeft.getCurrentPosition()) + "-"
                + df.format(extendRight.getPower()) + "-"
                + nf.format(extendRight.getTargetPosition()) + "-"
                + nf.format(extendRight.getCurrentPosition()) + "-"
                + df.format(harvester.getPower()) + "-"
//                + df.format(climbers.getPosition()) + "-"
                + runTime.time());
    }

    private void setDriveTarget(int target) {
        driveLeft.setTargetPosition(target);
        driveRight.setTargetPosition(target);
    }

    private void setDriveTarget(int targetLeft, int targetRight) {
        driveLeft.setTargetPosition(targetLeft);
        driveRight.setTargetPosition(targetRight);
    }

    private void setExtendTarget(int target) {
        extendLeft.setTargetPosition(target);
        extendRight.setTargetPosition(target);
    }

    private void setExtendTarget(int targetLeft, int targetRight) {
        extendLeft.setTargetPosition(targetLeft);
        extendRight.setTargetPosition(targetRight);
    }

    private void setDrivePower(double power) {
        driveLeft.setDesiredPower(power);
        driveRight.setDesiredPower(power);
    }

    private void setDrivePower(double powerLeft, double powerRight) {
        driveLeft.setDesiredPower(powerLeft);
        driveRight.setDesiredPower(powerRight);
    }

    private void setExtendPower(double power) {
        extendLeft.setDesiredPower(power);
        extendRight.setDesiredPower(power);
    }

    private void setExtendPower(double powerLeft, double powerRight) {
        extendLeft.setDesiredPower(powerLeft);
        extendRight.setDesiredPower(powerRight);
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

        if (Math.abs(driveLeft.getCurrentPosition() - driveLeft.getTargetPosition()) < 300) {
            if (Math.abs(driveLeft.getCurrentPosition() - driveLeft.getTargetPosition()) < 10) {
                driveLeft.setDesiredPower(0);
            } else if (driveLeft.getCurrentPosition() < driveLeft.getTargetPosition()) {
                driveLeft.setDesiredPower(0.3);
            } else if (driveLeft.getCurrentPosition() > driveLeft.getTargetPosition()){
                driveLeft.setDesiredPower(-0.3);
            } else {
                driveLeft.setDesiredPower(0);
            }
        }
        if (Math.abs(-driveRight.getTargetPosition() + driveRight.getTargetPosition()) < 300) {
            if (Math.abs(driveRight.getCurrentPosition() - driveRight.getTargetPosition()) < 10) {
                driveRight.setDesiredPower(0);
            } else if (driveRight.getCurrentPosition() < -driveRight.getTargetPosition()) {
                driveRight.setDesiredPower(0.3);
            } else if (driveRight.getCurrentPosition() > -driveRight.getTargetPosition()){
                driveRight.setDesiredPower(-0.3);
            } else {
                driveRight.setDesiredPower(0);
            }
        }
        if (driveLeft.getPower() < 0.01 && driveRight.getPower() < 0.01) {
            return true;
        } else {
            return false;
        }
    }

    private boolean extendDone() {
        if (Math.abs(extendLeft.getCurrentPosition() - extendLeft.getTargetPosition()) < 100) {
            if (Math.abs(extendLeft.getCurrentPosition() - extendLeft.getTargetPosition()) < 10) {
                extendLeft.setDesiredPower(0);
            } else if (extendLeft.getCurrentPosition() < extendLeft.getTargetPosition()) {
                extendLeft.setDesiredPower(0.2);
            } else {
                extendLeft.setDesiredPower(-0.2);
            }
        }
        if (Math.abs(extendRight.getTargetPosition() - extendRight.getTargetPosition()) < 10) {
            if (Math.abs(extendRight.getCurrentPosition() - extendRight.getTargetPosition()) < 10) {
                extendRight.setDesiredPower(0);
            } else if (extendRight.getCurrentPosition() < extendRight.getTargetPosition()) {
                extendRight.setDesiredPower(0.3);
            } else {
                extendRight.setDesiredPower(-0.3);
            }
        }
        if (extendLeft.getPower() < 0.01 && extendRight.getPower() < 0.01) {
            return true;
        } else {
            return false;
        }
    }

    private boolean timeDone() {
        if (runTime.time() >= sleepTime) {
            return true;
        } else {
            return false;
        }
    }


}