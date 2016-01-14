package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Button;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;

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
    }

    @Override
    protected void calculate() {
        motorMap.get("DriveLeftOne").stop();
        motorMap.get("DriveRightOne").stop();

        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bucketPower;
        if(bucketLeft){
            bucketPower = 1.0;
        }else if(bucketRight){
            bucketPower = -1.0;
        }else{
            bucketPower = 0.0;
        }
        motorMap.get("Bucket").setDesiredPower(bucketPower);
        /*
        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        if(bucketRight){
            motorMap.get("Bucket").setDesiredPower(1.0);
        }else if(bucketLeft) {
            motorMap.get("Bucket").setDesiredPower(-1.0);
        }*/

        boolean leftFlapUp = control.buttonHeld(Controller.C_ONE, Button.BUTTON_A);
        boolean leftFlapDown = control.button(Controller.C_ONE, Button.BUTTON_B);
        boolean rightFlapUp = control.buttonHeld(Controller.C_ONE, Button.BUTTON_X);
        boolean rightFlapDown = control.button(Controller.C_ONE, Button.BUTTON_Y);

        double lFlapPos = servoMap.get("BucketLeft").getPosition();
        double rFlapPos = servoMap.get("BucketRight").getPosition();
        if(leftFlapUp){
            lFlapPos += 0.01;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
            telemetry.addData("L-R", lFlapPos + " " + rFlapPos);
        }else if(leftFlapDown){
            lFlapPos -= 0.01;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
            telemetry.addData("L-R", lFlapPos + " " + rFlapPos);
        }
        if(rightFlapUp){
            rFlapPos += 0.01;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
            telemetry.addData("L-R", lFlapPos + " " + rFlapPos);
        }else if(rightFlapDown){
            rFlapPos -= 0.01;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
            telemetry.addData("L-R", lFlapPos + " " + rFlapPos);
        }

        boolean climberThrowUp = control.buttonHeld(Controller.C_TWO, Button.BUTTON_A);
        boolean climberThrowDown = control.buttonHeld(Controller.C_TWO, Button.BUTTON_B);
        double climberPos = servoMap.get("Climbers").getPosition();
        if(climberThrowUp){
            climberPos += 0.01;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
            telemetry.addData("Climbers", climberPos);
        }else if(climberThrowDown){
            climberPos -= 0.01;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
            telemetry.addData("Climbers", climberPos);
        }
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
