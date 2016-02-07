package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Button;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;
import com.qualcomm.ftcrobotcontroller.control.Trigger;

/**
 * Created by RM Robotics on 12/23/2015.
 */
public class R2D2 extends RMOpMode {
    //  private final String CONFIGURATION_PATH = "res/8121.json";
    double latchPos;
    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"MotorLeft\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorRight\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"ArmEOne\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"ArmETwo\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"armAngle1\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"armAngle2\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Winch\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"Forward\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Harvester\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"BucketDrop\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Latch\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"HitClimbers\",\n" +
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
        latchPos = .01;
    }

    @Override
    public void calculate() {
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("MotorLeft").setDesiredPower(leftPower);
        motorMap.get("MotorRight").setDesiredPower(rightPower);
        telemetry.addData("left power", leftPower);
        telemetry.addData("right power", rightPower);

        double armPower = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        motorMap.get("ArmEOne").setDesiredPower(armPower);
        motorMap.get("ArmETwo").setDesiredPower(armPower);

        double armAnglePower = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("armAngle1").setDesiredPower(armAnglePower);
        motorMap.get("armAngle2").setDesiredPower(armAnglePower);

        boolean bucketPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_Y);
        boolean bucketPressBack = control.buttonHeld(Controller.C_TWO, Button.BUTTON_A);
        double bucketPos = 0.01;
        if (bucketPress) {
            bucketPos = .305;
        } else if (bucketPressBack) {
            bucketPos = 0.01;
        }
        servoMap.get("BucketDrop").setDesiredPosition(bucketPos);

        double latchPress = control.triggerValue(Controller.C_ONE, Trigger.T_RIGHT);
        double latchPressBack = control.triggerValue(Controller.C_ONE, Trigger.T_LEFT);
        if (latchPress > .2) {
            latchPos = .5;
        } else if (latchPressBack > .2) {
            latchPos = 0.01;
        }
        servoMap.get("Latch").setDesiredPosition(latchPos);

        boolean hitClimberPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        boolean hitClimberPressBack = control.buttonHeld(Controller.C_TWO, Button.BUTTON_RB);
        double hitClimberPos = 0.01;
        if (hitClimberPress) {
            hitClimberPos = .5;
        } else if (hitClimberPressBack) {
            hitClimberPos = 0.01;
        }
        servoMap.get("HitClimbers").setDesiredPosition(hitClimberPos);

        boolean harvesterForward = control.buttonHeld(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvesterReverse = control.buttonHeld(Controller.C_ONE, Button.BUTTON_RB);
        double harvesterPower;
        if (harvesterForward) {
            harvesterPower = .5;
        } else if (harvesterReverse) {
            harvesterPower = -.5;
        } else {
            harvesterPower = 0;
        }
        motorMap.get("Harvester").setDesiredPower(harvesterPower);

        boolean WinchForward = control.buttonHeld(Controller.C_ONE, Button.BUTTON_B);
        boolean WinchReverse = control.buttonHeld(Controller.C_ONE, Button.BUTTON_A);
        double WinchPower;
        if (WinchForward) {
            WinchPower = .5;
        } else if (WinchReverse) {
            WinchPower = -.5;
        } else {
            WinchPower = 0;
        }
        motorMap.get("Winch").setDesiredPower(WinchPower);

    }


    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
