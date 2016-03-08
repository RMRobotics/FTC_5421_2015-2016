package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;

import com.qualcomm.robotcore.hardware.DcMotorController;
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

public class TestifyOp5421 extends RMOpMode {
    DecimalFormat df = new DecimalFormat("#.##");

    Motor driveLeft;
    Motor driveRight;
    Motor extendLeft;
    Motor extendRight;
    //Motor winchLeft;
    //Motor winchRight;
    Motor harvester;
    //rServo climbers;
    rServo leftFlap;
    rServo rightFlap;
    rServo bucket;
    //rServo leftHook;
    //rServo rightHook;
    //rServo clearLeft;
    //rServo clearRight;
    ElapsedTime runTime;

    @Override
    public void init() {
        super.setTeam(5421);
        super.init();
        driveLeft = motorMap.get("mL");
        driveRight = motorMap.get("mR");
        extendLeft = motorMap.get("eL");
        extendRight = motorMap.get("eR");
        //winchLeft = motorMap.get("wL");
        //winchRight = motorMap.get("wR");
        harvester = motorMap.get("h");
        //climbers = servoMap.get("climbers");
        leftFlap = servoMap.get("bL");
        rightFlap = servoMap.get("bR");
        bucket = servoMap.get("bucket");
        bucket.setDesiredPosition(0.5);
        //leftHook = servoMap.get("leftHook");
        //rightHook = servoMap.get("rightHook");
        //clearLeft = servoMap.get("aL");
        //clearRight = servoMap.get("aR");
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        addTelemetry();
        runTime.reset();
    }

    @Override
    protected void calculate() {

        for (Motor m : motorMap.values()) {
            if (m.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
                m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            }
        }

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

        //winch
        /*double windLeft = control.triggerValue(Controller.C_ONE, Trigger.T_LEFT);
        double windRight = control.triggerValue(Controller.C_ONE, Trigger.T_RIGHT);
        boolean unwindLeft = control.dpadValue(Controller.C_ONE, Dpad.DPAD_LEFT);
        boolean unwindRight = control.dpadValue(Controller.C_ONE, Dpad.DPAD_RIGHT);
        boolean winchDown = control.dpadValue(Controller.C_ONE, Dpad.DPAD_DOWN);
        if (winchDown) {
            winchLeft.setDesiredPower(-1);
            winchRight.setDesiredPower(-1);
        } else if (unwindLeft) {
            winchLeft.setDesiredPower(-1);
            winchRight.setDesiredPower(0);
        } else if (unwindRight) {
            winchLeft.setDesiredPower(0);
            winchRight.setDesiredPower(-1);
        } else if (windLeft > 0.2) {
            winchLeft.setDesiredPower(1);
            winchRight.setDesiredPower(0);
        } else if (windRight > 0.2) {
            winchLeft.setDesiredPower(0);
            winchRight.setDesiredPower(1);
        } else {
            winchLeft.setDesiredPower(0);
            winchRight.setDesiredPower(0);
        }*/

        /*//climbers
        boolean climberOpen = control.button(Controller.C_ONE, Button.BUTTON_A);
        boolean climberClose = control.button(Controller.C_ONE, Button.BUTTON_B);
        if (climberOpen) {
            climbers.setDesiredPosition(0);
        } else if (climberClose) {
            climbers.setDesiredPosition(1);
        }*/

        //bucket
        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bucketSpeed;
        if(bucketRight){
            bucketSpeed = 1.0;
        }else if(bucketLeft) {
            bucketSpeed = 0;
        }else{
            bucketSpeed = 0.5;
        }
        bucket.setDesiredPosition(bucketSpeed);

        //flaps
        double flapLeft = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double flapRight = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos;
        double rFlapPos;
        if(flapLeft > 0.2){
            lFlapPos = 0.5;
            leftFlap.setDesiredPosition(lFlapPos);
        }else if(flapLeft < -0.2){
            lFlapPos = 0;
            leftFlap.setDesiredPosition(lFlapPos);
        }
        if(flapRight > 0.2){
            rFlapPos = 0.4;
            rightFlap.setDesiredPosition(rFlapPos);
        }else if(flapRight < -0.2){
            rFlapPos = 1;
            rightFlap.setDesiredPosition(rFlapPos);
        }

        //extender
        boolean extend = control.dpadValue(Controller.C_TWO, Dpad.DPAD_UP);
        boolean retract = control.dpadValue(Controller.C_TWO, Dpad.DPAD_DOWN);
        if (extend) {
            extendLeft.setDesiredPower(0.3);
            extendRight.setDesiredPower(0.3);
        } else if (retract) {
            extendLeft.setDesiredPower(-0.3);
            extendRight.setDesiredPower(-0.3);
        } else {
            extendLeft.setDesiredPower(0);
            extendRight.setDesiredPower(0);
        }

        /*//all clear signal
        boolean reset = control.button(Controller.C_TWO, Button.BUTTON_Y);
        boolean leftDown = control.button(Controller.C_TWO, Button.BUTTON_X);
        boolean rightDown = control.buttonPressed(Controller.C_TWO, Button.BUTTON_B);
        if (reset) {
            clearLeft.setInitPos();
            clearRight.setInitPos();
        } else if (leftDown) {
            clearLeft.setDesiredPosition(1.0);
        } else if (rightDown) {
            clearRight.setDesiredPosition(1.0);
        }*/
        addTelemetry();
    }

    @Override
    protected String setConfigurationPath() {
        final String CONFIGURATION_PATH = "{\n" +
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
                "}";
        return CONFIGURATION_PATH;
    }

    private void addTelemetry() {
        telemetry.addData("L-R-LE-RE-H-C-LF-RF-B-LH-RH-T", df.format(driveLeft.getPower()) + "-"
                + df.format(driveRight.getPower()) + "-"
                + df.format(extendLeft.getPower()) + "-"
                + df.format(extendRight.getPower()) + "-"
                //+ df.format(winchLeft.getPower()) + "-"
                //+ df.format(winchRight.getPower()) + "-"
                + df.format(harvester.getPower()) + "-"
                //+ df.format(climbers.getPosition()) + "-"
                + df.format(leftFlap.getPosition()) + "-"
                + df.format(rightFlap.getPosition()) + "-"
                + df.format(bucket.getPosition()) + "-"
                //+ df.format(leftHook.getPosition()) + "-"
                //+ df.format(rightHook.getPosition()) + "-"
                //+ df.format(clearLeft.getPosition()) + "-"
                //+ df.format(clearRight.getPosition()) + "-"
                + runTime.time());
    }
}
