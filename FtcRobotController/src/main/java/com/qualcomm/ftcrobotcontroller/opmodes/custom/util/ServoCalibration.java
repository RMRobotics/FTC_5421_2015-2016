package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;

public class ServoCalibration extends RMOpMode {

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
        super.setTeam(25421);
        super.init();
    }

    @Override
    protected void calculate() {

        /*
        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        if(bucketRight){
            motorMap.get("Bucket").setDesiredPower(1.0);
        }else if(bucketLeft) {
            motorMap.get("Bucket").setDesiredPower(-1.0);
        }*/

        double leftFlap = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos = servoMap.get("bL").getPosition();
        double rFlapPos = servoMap.get("bR").getPosition();
        if(leftFlap > 0.2){
            lFlapPos += 0.01;
            servoMap.get("bL").setDesiredPosition(lFlapPos);
        }else if(leftFlap < -0.2){
            lFlapPos -= 0.01;
            servoMap.get("bL").setDesiredPosition(lFlapPos);
        }
        if(rightFlap > 0.2){
            rFlapPos += 0.01;
            servoMap.get("bR").setDesiredPosition(rFlapPos);
        }else if(rightFlap < -0.2){
            rFlapPos -= 0.01;
            servoMap.get("bR").setDesiredPosition(rFlapPos);
        }
        telemetry.addData("L-LJ-R-RJ", lFlapPos + " " + leftFlap + " " + rFlapPos + " " + rightFlap);
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
