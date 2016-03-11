package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
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
    double turnDegree;

    GyroSensor gyro;

    Motor driveLeft;
    Motor slaveLeft;
    Motor driveRight;
    Motor slaveRight;
    Motor harvester;
    rServo climbers;
    ElapsedTime runTime;

    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat nf = new DecimalFormat("########");

    //private final String CONFIGURATION_PATH = "res/5421robot_cleanUpImprovement.JSON";

    @Override
    public void init() {
        super.setTeam(25421);
        super.init();
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        driveLeft = motorMap.get("mL");
        slaveLeft = motorMap.get("sL");
        driveRight = motorMap.get("mR");
        slaveRight = motorMap.get("sR");
        harvester = motorMap.get("h");
        //climbers = servoMap.get("climbers");
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        addTelemetry();
        runTime.reset();
    }

    @Override
    protected void calculate() {
        for (Motor m : motorMap.values()) {
            if (m.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) {
                m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            }
        }
        switch (state) {
            case 1:
                turnDegree = 45;
                setDrivePower(0, 0.3);
                updateState(StateType.GYRO);
                break;
            /*case 1:
                sleepTime = 10000;
                updateState(StateType.SLEEP);
                addTelemetry();
                break;
            case 2:
                harvester.setDesiredPower(1);
                setDriveTarget(10000);
                setDrivePower(0.5);
                updateState(StateType.ENCODER_DRIVE);
                addTelemetry();
                break;
            /*case 2:
                driveLeft.setTargetPosition(10000);
                setDrivePower(0.5, 0);
                updateState(StateType.ENCODER_DRIVE);
                addTelemetry();
                break;*/
            case 2:
                stop();
                break;
            case WAIT:
                switch (prevStateType) {
                    case ENCODER_DRIVE:
                        if (driveDone()) {
                            updateStateWait();
                            driveStop();
                        }
                        break;
                    case SLEEP:
                        if (timeDone()) {
                            updateStateWait();
                        }
                        break;
                    case GYRO:
                        if (gyroDone()) {
                            updateStateWait();
                        }
                        break;
                }
            default:
                stop();
        }
        updateSlave();
        addTelemetry();
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
        telemetry.addData("S-P-L-LT-LP-R-RT-RP-LE-LET-LEP-RE-RET-REP-H-C-T", state + "-" + prevState + "-" + df.format(driveLeft.getPower()) + "-"
                + nf.format(driveLeft.getTargetPosition()) + "-"
                + nf.format(driveLeft.getCurrentPosition()) + "-"
                + df.format(driveRight.getPower()) + "-"
                + nf.format(driveRight.getTargetPosition()) + "-"
                + nf.format(driveRight.getCurrentPosition()) + "-"
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

    private void updateSlave() {
        slaveLeft.setDesiredPower(driveLeft.getDesiredPower());
        slaveRight.setDesiredPower(driveRight.getDesiredPower());
    }

    private boolean driveDone() {

        if (Math.abs(driveLeft.getCurrentPosition()) > driveLeft.getTargetPosition()) {
            driveLeft.setDesiredPower(0);
        }
        if (Math.abs(driveRight.getCurrentPosition()) > driveRight.getTargetPosition()) {
            driveRight.setDesiredPower(0);
        }
        if (driveLeft.getPower() < 0.01 && driveRight.getPower() < 0.01) {
            return true;
        } else {
            return false;
        }
    }

    private boolean gyroDone() {
        if (Math.abs(gyro.getHeading() - turnDegree) < 3) {
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

    private void driveStop() {
        driveLeft.setDesiredPower(0);
        driveRight.setDesiredPower(0);
    }

}