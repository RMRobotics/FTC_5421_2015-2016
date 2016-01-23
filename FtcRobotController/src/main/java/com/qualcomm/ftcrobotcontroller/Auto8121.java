package com.qualcomm.ftcrobotcontroller;

import java.lang.Math;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Button;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;

/**
 * Created by decla on 1/9/2016.
 */
public class Auto8121 extends RMOpMode {

    private int state = 1;
    //  private final String CONFIGURATION_PATH = "res/8121.json";
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
            "      \"name\":\"Arm\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"armAngle\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Bucket\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"BucketMove\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"BucketRight\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void calculate() {
        switch(state){
            case 1:
                motorMap.get("DriveLeftOne").setEncoderMove(0,5,1.0);
                motorMap.get("DriveLeftTwo").setEncoderMove(0,5,1.0);
                if ((Math.abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10)&&((Math.abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftTwo").getTargetPosition()) < 10))){
                    state = 2;
                }
            case 2:
                motorMap.get("DriveRightOne").setEncoderMove(0,5,1.0);
                motorMap.get("DriveRightTwo").setEncoderMove(0,5,1.0);
                if ((Math.abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftOne").getTargetPosition()) < 10)&&((Math.abs(motorMap.get("DriveLeftOne").getCurrentPosition() - motorMap.get("DriveLeftTwo").getTargetPosition()) < 10))){
                    state = 3;
                }
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                return;
        }
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
