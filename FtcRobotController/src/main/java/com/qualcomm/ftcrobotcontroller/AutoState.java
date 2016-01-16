package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.Calendar;
import static java.lang.Math.abs;

/**
 * Created by Simon on 12/31/2015.
 */

public class AutoState extends RMOpMode {
    private int state = 1;
    Calendar cal;
    protected long curTime;
    protected long startTime;
    private double currentPositionLeft;
    private double currentPositionRight;

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
            "      \"init\":0.34,\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.6,\n" +
            "    }\n" +
            "  ],\n" +
            "  \"slave\":[\n" +
            "    {\n" +
            "      \"name\":\"DriveLeftTwo\",\n" +
            "      \"slaveTo\":\"DriveLeftOne\",\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"DriveRightTwo\",\n" +
            "      \"slaveTo\":\"DriveRightOne\",\n" +
            "    },\n" +
            "  ],\n" +
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
                currentPositionLeft = motorMap.get("DriveLeftOne").getCurrentPosition();
                currentPositionRight = motorMap.get("DriveRightOne").getCurrentPosition();
                if (motorMap.get("DriveLeftOne").getMode() == DcMotorController.RunMode.RUN_TO_POSITION) {
                    state = 2;
                } else {
                    for (Motor m : motorMap.values()) {
                        m.runToPosition();
                    }
                    state = 4;
                }
                /*case 2: //drive to center
                    motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, 2.0, 0.5);
                    //motorMap.get("DriveLeftTwo").setEncoderMove(0, 2.0, 0.5);
                    motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, 2.0, 0.5);
                    //motorMap.get("DriveRightTwo").setEncoderMove(0, 2.0, 0.5);
                    telemetry.addData("State " + state + " L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorMap.get("DriveRightOne").getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition());
                    if (abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10 && abs(motorMap.get("DriveRightOne").getCurrentPosition() - motorMap.get("DriveRightOne").getTargetPosition()) < 10) {
                        currentPositionLeft = motorMap.get("DriveLeftOne").getCurrentPosition();
                        currentPositionRight = motorMap.get("DriveRightOne").getCurrentPosition();
                        state = 3;
                    }
                case 3: //turn left 45 degree
                    motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, 0, 0.5);
                    //motorMap.get("DriveLeftTwo").setEncoderMove(0, 0, 0.5);
                    motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, 2.0, 0.5);
                    //motorMap.get("DriveRightTwo").setEncoderMove(0, 2.0, 0.5);
                    telemetry.addData("State " + state + " L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorMap.get("DriveRightOne").getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition());
                    if (abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10 && abs(motorMap.get("DriveRightOne").getCurrentPosition() - motorMap.get("DriveRightOne").getTargetPosition()) < 10) {
                        currentPositionLeft = motorMap.get("DriveLeftOne").getCurrentPosition();
                        currentPositionRight = motorMap.get("DriveRightOne").getCurrentPosition();
                        state = 4;
                    }*/
            case 4: //drive to beacon zone
                motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, 10, 1.0);
                //motorMap.get("DriveLeftTwo").setEncoderMove(0, 0, 0.5);
                motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, 10, 1.0);
                //motorMap.get("DriveRightTwo").setEncoderMove(0, 2.0, 0.5);
                telemetry.addData("State " + state + " L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorMap.get("DriveRightOne").getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition());
                if (abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10 && abs(motorMap.get("DriveRightOne").getCurrentPosition() - motorMap.get("DriveRightOne").getTargetPosition()) < 10) {
                    currentPositionLeft = motorMap.get("DriveLeftOne").getCurrentPosition();
                    currentPositionRight = motorMap.get("DriveRightOne").getCurrentPosition();
                    state = 0;
                }
                /*case 5: //dump climbers
                    telemetry.addData("State " + state + " L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorMap.get("DriveRightOne").getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition());
                    servoMap.get("Climbers").setDesiredPosition(0); //TODO: set values for servo position
                    telemetry.addData("State " + state, servoMap.get("Climbers").getPosition());
                    waitTime(2000);
                    servoMap.get("Climbers").setDesiredPosition(0.9);
                    waitTime(2000);
                    servoMap.get("Climbers").setDesiredPosition(0);
                    state = 6;
                case 6:
                    motorMap.get("DriveLeftOne").setEncoderMove(currentPositionLeft, -2.0, 0.5);
                    motorMap.get("DriveRightOne").setEncoderMove(currentPositionRight, -2.0, 0.5);
                    telemetry.addData("State " + state + " L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getCurrentPosition() + "-" + motorMap.get("DriveRightOne").getCurrentPosition() + "-" + motorMap.get("DriveRightTwo").getCurrentPosition());
                    if (abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10 && abs(motorMap.get("DriveRightOne").getCurrentPosition() - motorMap.get("DriveRightOne").getTargetPosition()) < 10) {
                        currentPositionLeft = motorMap.get("DriveLeftOne").getCurrentPosition();
                        currentPositionRight = motorMap.get("DriveRightOne").getCurrentPosition();
                        state = 7;
                    }*/
                //TODO: Add more states
            default:
                telemetry.addData("breaking", "broken");
                break;
        }
    }

    private void waitTime(long wait) {
        curTime = cal.getTimeInMillis();
        startTime = cal.getTimeInMillis();
        while ((curTime-startTime)<wait){
            telemetry.addData("wait","Waiting");
            DbgLog.msg("Waiting");
            curTime = cal.getTimeInMillis();
        }
    }
}