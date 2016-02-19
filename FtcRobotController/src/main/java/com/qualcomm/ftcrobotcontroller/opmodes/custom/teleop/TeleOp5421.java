package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;

import java.text.DecimalFormat;

public class TeleOp5421 extends RMOpMode {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat nf = new DecimalFormat("########");

    Motor driveLeft;
    Motor driveRight;
    Motor extendLeft;
    Motor extendRight;
    Motor winchLeft;
    Motor winchRight;
    Motor harvester;
    rServo climbers;
    rServo leftFlap;
    rServo rightFlap;
    rServo bucket;
    rServo leftHook;
    rServo rightHook;
    ElapsedTime runTime;

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
        super.setTeam(5421);
        super.init();
        driveLeft = motorMap.get("driveLeft");
        driveRight = motorMap.get("driveRight");
        extendLeft = motorMap.get("extendLeft");
        extendRight = motorMap.get("extendRight");
        winchLeft = motorMap.get("winchLeft");
        winchRight = motorMap.get("winchRight");
        harvester = motorMap.get("harvester");
        climbers = servoMap.get("climbers");
        leftFlap = servoMap.get("leftFlap");
        rightFlap = servoMap.get("rightFlap");
        bucket = servoMap.get("bucket");
        leftHook = servoMap.get("leftHook");
        rightHook = servoMap.get("rightHook");
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        addTelemetry();
        runTime.reset();
    }

    @Override
    protected void calculate() {
        addTelemetry();

        //drive
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        driveLeft.setDesiredPower(leftPower);
        driveRight.setDesiredPower(rightPower);

        //harvester
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
        harvester.setDesiredPower(harvestPower);

        //bucket
        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bucketSpeed;
        if(bucketRight){
            bucketSpeed = 1.0;
        }else if(bucketLeft) {
            bucketSpeed = -1.0;
        }else{
            bucketSpeed = 0.0;
        }
        bucket.setDesiredPosition(bucketSpeed);

        //flaps
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

        //extender
        boolean extend = control.dpadValue(Controller.C_TWO, Dpad.DPAD_UP);
        boolean retract = control.dpadValue(Controller.C_TWO, Dpad.DPAD_DOWN);
        if (extend) {
            extendLeft.setDesiredPower(0.5);
            extendRight.setDesiredPower(0.5);
        } else if (retract) {
            extendLeft.setDesiredPower(-0.5);
            extendRight.setDesiredPower(-0.5);
        } else {
            extendLeft.setPowerFloat();
            extendRight.setPowerFloat();
        }

        //climbers
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
        telemetry.addData("L-R-LE-RE-H-C-LF-RF-B-LH-RH-T", df.format(driveLeft.getPower()) + "-"
                + df.format(driveRight.getPower()) + "-"
                + df.format(extendLeft.getPower()) + "-"
                + df.format(extendRight.getPower()) + "-"
                + df.format(harvester.getPower()) + "-"
                + df.format(climbers.getPosition()) + "-"
                + df.format(leftFlap.getPosition()) + "-"
                + df.format(rightFlap.getPosition()) + "-"
                + df.format(bucket.getPosition()) + "-"
                + df.format(leftHook.getPosition()) + "-"
                + df.format(rightHook.getPosition()) + "-"
                + runTime.time());
    }
}
