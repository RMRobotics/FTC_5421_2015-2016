package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Button;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;

public class TeleOp extends RMOpMode {

    //private final String CONFIGURATION_PATH = "res/robot.json";
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
            "      \"init\":0.45,\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"BucketLeft\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.9,\n" +
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
    }

    @Override
    protected void calculate() {
        opType = 1;
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("DriveLeftOne").setDesiredPower(leftPower);
        //motorMap.get("DriveLeftTwo").setDesiredPower(leftPower);
        motorMap.get("DriveRightOne").setDesiredPower(rightPower);
        //motorMap.get("DriveRightTwo").setDesiredPower(rightPower);

        boolean harvestUp = control.button(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvestDown = control.button(Controller.C_ONE, Button.BUTTON_RB);
        double harvestPower;
        if(harvestUp){
            harvestPower = -1.0;
        }else if(harvestDown){
            harvestPower = 1.0;
        }else{
            harvestPower = 0.0;
        }
        motorMap.get("Harvester").setDesiredPower(harvestPower);

        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bucketPower;
        if(bucketRight){
            bucketPower = 1.0;
        }else if(bucketLeft) {
            bucketPower = -1.0;
        }else{
            bucketPower = 0.0;
        }
        motorMap.get("Bucket").setDesiredPower(bucketPower);

        double leftFlap = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos;
        double rFlapPos;
        if(leftFlap > 0.1){
            lFlapPos = 0.9;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }else if(leftFlap < -0.1){
            lFlapPos = 0.3;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }
        if(rightFlap > 0.1){
            rFlapPos = 0.45;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
        }else if(rightFlap < -0.1){
            rFlapPos = 1.0;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
        }

        boolean climberThrowUp = control.button(Controller.C_TWO, Button.BUTTON_A);
        boolean climberThrowDown = control.button(Controller.C_TWO, Button.BUTTON_B);
        double climberPos;
        if(climberThrowUp){
            climberPos = 0.6;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
        }else if(climberThrowDown){
            climberPos = 0;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
        }
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
