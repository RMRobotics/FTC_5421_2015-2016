package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;

import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.control.Trigger;
import com.rmrobotics.library.core.RMOpMode;

import java.text.DecimalFormat;

public class TeleOp5421 extends RMOpMode {
    DecimalFormat df = new DecimalFormat("#.##");

    private final String CONFIGURATION_PATH = "res/5421robot_cleanUpImprovement.JSON";
    /*private final String CONFIGURATION_PATH = "{\n" +
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
            "}";*/

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void calculate() {
        addTelemetry();
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("DriveLeftOne").setDesiredPower(leftPower);
        //motorMap.get("DriveLeftTwo").setDesiredPower(leftPower);
        motorMap.get("DriveRightOne").setDesiredPower(rightPower);
        //motorMap.get("DriveRightTwo").setDesiredPower(rightPower);

        boolean harvestUp = control.button(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvestDown = control.button(Controller.C_ONE, Button.BUTTON_RB);
        double halfHarvestUp = control.triggerValue(Controller.C_ONE, Trigger.T_LEFT);
        double harvestPower;
        if(harvestUp){
            harvestPower = -1.0;
        }else if(harvestDown){
            harvestPower = 1.0;
        }else if(halfHarvestUp > 0.4){
            harvestPower = halfHarvestUp;
        }else{
            harvestPower = 0.0;
        }
        motorMap.get("Harvester").setDesiredPower(harvestPower);

        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double triggerLeft = control.triggerValue(Controller.C_TWO, Trigger.T_LEFT);
        double triggerRight = control.triggerValue(Controller.C_TWO, Trigger.T_RIGHT);
        double bucketPower;
        if(bucketRight){
            bucketPower = 1.0;
        }else if(bucketLeft) {
            bucketPower = -1.0;
        }else if(triggerLeft > 0.4){
            bucketPower = -triggerLeft/3;
        }else if(triggerRight > 0.4){
            bucketPower = triggerRight/3;
        }else{
            bucketPower = 0.0;
        }
        motorMap.get("Bucket").setDesiredPower(bucketPower);

        double leftFlap = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos;
        double rFlapPos;
        if(leftFlap > 0.2){
            lFlapPos = 1.0;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }else if(leftFlap < -0.2){
            lFlapPos = 0.36;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }
        if(rightFlap > 0.2){
            rFlapPos = 0.36;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
        }else if(rightFlap < -0.2){
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
        addTelemetry();
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
}
