package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.Calendar;

import static java.lang.Math.abs;

/**
 * Created by Simon on 12/31/2015.
 */

public class AutoState extends RMOpMode {
    private int state = 1;
    Calendar cal;
    protected long startTime;
    private double startPositionLeft;
    private double startPositionRight;
    private Motor motorLeft = motorMap.get("DriveLeftOne");
    private Motor motorRight = motorMap.get("DriveRightOne");
    private Motor harvester = motorMap.get("Harvester");
    private rServo climbers = servoMap.get("Climbers");

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"DriveLeftOne\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"DriveLeftTwo\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"DriveRightOne\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"DriveRightTwo\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Harvester\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Bucket\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" + //forward is counterclockwise
            "    }\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"BucketRight\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":1.0,\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"BucketLeft\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.37,\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.6,\n" +
            "    }\n" +
            "  ],\n" +
            /*"  \"slave\":[\n" +
            "    {\n" +
            "      \"name\":\"DriveLeftTwo\",\n" +
            "      \"slaveTo\":\"DriveLeftOne\",\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"DriveRightTwo\",\n" +
            "      \"slaveTo\":\"DriveRightOne\",\n" +
            "    },\n" +
            "  ],\n" +*/
            "}";

    @Override
    public void init() {
        super.init();

        cal = Calendar.getInstance();
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    public void calculate() {
        opType = 0;
        switch (state) {
            case 1: //begin
                startPositionLeft = motorLeft.getCurrentPosition();
                startPositionRight = motorRight.getCurrentPosition();
                if (motorLeft.getMode() == DcMotorController.RunMode.RUN_TO_POSITION && motorRight.getMode() == DcMotorController.RunMode.RUN_TO_POSITION) {
                    state = 2;
                } else {
                    motorLeft.runToPosition();
                    motorRight.runToPosition();
                    state = 2;
                }
                break;
            case 2: //drive to center
                encoderStraight(2.0, 1.0);
                //motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, 2.0, 0.5);
                //motorMap.get("DriveLeftTwo").setEncoderMove(0, 2.0, 0.5);
                //motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, 2.0, 0.5);
                //motorMap.get("DriveRightTwo").setEncoderMove(0, 2.0, 0.5);
                addTelemetry();
                quitCheck();
                break;
            case 3: //turn left 45 degree
                encoderLeft(2.0, 1.0);
                //motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, 0, 0.5);
                //motorMap.get("DriveLeftTwo").setEncoderMove(0, 0, 0.5);
                //motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, 2.0, 0.5);
                //motorMap.get("DriveRightTwo").setEncoderMove(0, 2.0, 0.5);
                addTelemetry();
                quitCheck();
                break;
            case 4: //drive to beacon zone
                encoderStraight(10.0, 1.0);
                addTelemetry();
                quitCheck();
                break;
            case 5: //dump climbers
                climbers.setDesiredPosition(climbers.getMaxPos());
                addTelemetry();
                quitCheck(2000);
                break;
            case 6:
                climbers.setDesiredPosition(climbers.getMinPos());
                addTelemetry();
                quitCheck(2000);
                break;
            case 7:
                encoderStraight(-5.0, 1.0);
                addTelemetry();
                quitCheck();
                break;
            case 8:
                encoderRight(4.0, 1.0);
                addTelemetry();
                quitCheck();
                break;
            case 9:
                encoderStraight(-5.0, 1.0);
                addTelemetry();
                quitCheck();
                break;
            case 10:
                break;
            default:
                telemetry.addData("breaking", "broken");
                break;
        }
    }

    private void encoderStraight(double rotation, double power) {
        motorLeft.setEncoderMove(startPositionLeft, rotation, power);
        motorRight.setEncoderMove(startPositionRight, rotation, power);
    }

    private void encoderLeft(double rotation, double power) {
        motorRight.setEncoderMove(startPositionRight, rotation, power);
    }

    private void encoderRight(double rotation, double power) {
        motorLeft.setEncoderMove(startPositionLeft, rotation, power);
    }

    private void addTelemetry() {
        telemetry.addData("State-L1-L2-R1-R2-H-C", state + "-" + motorLeft.getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorRight.getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition() + "-" + harvester.getCurrentPosition() + "-" + climbers.getPosition());
    }

    private void quitCheck() {
        if (abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) < 10 && abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) < 10) {
            startPositionLeft = motorLeft.getCurrentPosition();
            startPositionRight = motorRight.getCurrentPosition();
            startTime = cal.getTimeInMillis();
            state += 1;
        }
    }

    private void quitCheck(long wait) {
        if ((cal.getTimeInMillis() - startTime) > wait) {
            startPositionLeft = motorLeft.getCurrentPosition();
            startPositionRight = motorRight.getCurrentPosition();
            startTime = cal.getTimeInMillis();
            state += 1;
        }
    }
}