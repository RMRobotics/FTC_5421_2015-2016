package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.Calendar;

/**
 * Created by Simon on 12/31/2015.
 */

public class AutoState extends RMOpMode {
    private int state = 1;
    Calendar cal;
    protected long curTime;
    protected long startTime;

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
            "    {\n" +
            "      \"name\":\"Harvester\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Bucket\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"BucketRight\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"BucketLeft\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "    }\n" +
            "  ]\n" +
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
        while (state != 0) {
            switch (state) {
                case 1: //begin
                    if (motorMap.get("DriveLeftOne").getMode() == DcMotorController.RunMode.RUN_USING_ENCODERS) {
                        state = 2;
                    } else {
                        for (Motor m : motorMap.values()) {
                            m.runToPosition();
                        }
                        state = 2;
                    }
                case 2: //drive to center
                    motorMap.get("DriveLeftOne").setEncoderMove(motorMap.get("DriveLeftOne").getCurrentPosition(),2.0,0.5);
                    motorMap.get("DriveLeftTwo").setEncoderMove(motorMap.get("DriveLeftTwo").getCurrentPosition(),2.0,0.5);
                    motorMap.get("DriveRightOne").setEncoderMove(motorMap.get("DriveRightOne").getCurrentPosition(),2.0,0.5);
                    motorMap.get("DriveRightTwo").setEncoderMove(motorMap.get("DriveRightTwo").getCurrentPosition(),2.0,0.5);
                    telemetry.addData("L1-L2-R1-R2", motorMap.get("DriveLeftOne").getCurrentPosition() + "-" + motorMap.get("DriveLeftTwo").getTargetPosition() + "-" + motorMap.get("DriveRightOne").getTargetPosition() + "-" + motorMap.get("DriveRightTwo").getTargetPosition());
                    if (motorMap.get("DriveLeftOne").getCurrentPosition() > motorMap.get("DriveLeftOne").getTargetPosition()) {
                        state = 3;
                    }
                case 3: //turn left 45 degree
                    telemetry.addData("STATE 3", "TURN LEFT 45");
                    motorMap.get("DriveLeftOne").setDesiredPower(0.0);
                    motorMap.get("DriveLeftTwo").setDesiredPower(0.0);
                    motorMap.get("DriveRightOne").setDesiredPower(1.0);
                    motorMap.get("DriveRightTwo").setDesiredPower(1.0);
                    waitTime(1000);
                    state = 4;
                case 4: //drive to beacon zone
                    telemetry.addData("STATE 4", "ALL PREVIOUS STATES SUCCESSFUL");
                    waitTime(10000);
                    state = 0;
                default:
                    break;
            } // end switch

        } // end while
        /*
        while (state != 0) {
            switch (state) {
                case 1:
                    motorMap.get("DriveLeftOne").setRotationDistance(1.0);
                    motorMap.get("DriveLeftTwo").setRotationDistance(1.0);
                    motorMap.get("DriveRightOne").setRotationDistance(1.0);
                    motorMap.get("DriveRightTwo").setRotationDistance(1.0);
                default:
                    break;
            }
        } */
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