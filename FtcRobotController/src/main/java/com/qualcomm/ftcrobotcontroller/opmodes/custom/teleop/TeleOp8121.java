package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;


import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;

/**
 * Created by RM Robotics on 12/23/2015.
 */
public class TeleOp8121 extends RMOpMode {
    //  private final String CONFIGURATION_PATH = "res/8121.json";
    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"MotorL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorExtendL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorExtendR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"MotorUpL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"MotorUpR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Winch\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
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
            "      \"name\":\"BucketSeeSaw\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"BucketLeft\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"BucketRight\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Latch1\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Latch2\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
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
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("MotorL").setDesiredPower(leftPower);
        motorMap.get("MotorR").setDesiredPower(rightPower);

        boolean armPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_X);
        boolean armRetract = control.buttonHeld(Controller.C_TWO, Button.BUTTON_Y);
        double armPower;
        if(armPress){
            armPower = .5;
        }else if(armRetract){
            armPower = -.5;
        }else{
            armPower = 0;
        }
        motorMap.get("MotorExtendL").setDesiredPower(armPower);
        motorMap.get("MotorExtendR").setDesiredPower(armPower);

        boolean armAngle = control.buttonHeld(Controller.C_TWO, Button.BUTTON_B);
        boolean armAngleBackward = control.buttonHeld(Controller.C_TWO, Button.BUTTON_A);
        double armAnglePower;
        if(armAngle){
            armAnglePower = .5;
        }else if(armAngleBackward){
            armAnglePower = -.5;
        }else{
            armAnglePower = 0;
        }
        motorMap.get("MotorUpL").setDesiredPower(armAnglePower);
        motorMap.get("MotorUpR").setDesiredPower(armAnglePower);

        boolean winchPress = control.buttonHeld(Controller.C_ONE, Button.BUTTON_A);
        boolean winchBack = control.buttonHeld(Controller.C_ONE, Button.BUTTON_B);
        double winchPower;
        if(winchPress){
            winchPower = .5;
        }else if(winchBack){
            winchPower = -.5;
        }else{
            winchPower = 0;
        }
        motorMap.get("Winch").setDesiredPower(winchPower);

        boolean harvesterForward = control.buttonHeld(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvesterReverse = control.buttonHeld(Controller.C_ONE, Button.BUTTON_RB);
        double harvesterPower;
        if(harvesterForward){
            harvesterPower = .5;
        }else if(harvesterReverse){
            harvesterPower = -.5;
        }
        else{
            harvesterPower = 0;
        }
        motorMap.get("Harvester").setDesiredPower(harvesterPower);

        boolean bucketLeft = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.buttonHeld(Controller.C_TWO, Button.BUTTON_RB);
        double bucketPosition;
        if(bucketLeft){
            bucketPosition = 0;
        }else if(bucketRight){
            bucketPosition = 1;
        }
        else{
            bucketPosition = .5;
        }
        servoMap.get("BucketSeeSaw").setDesiredPosition(bucketPosition);

        boolean bucketOpen = control.dpadValue(Controller.C_TWO, Dpad.DPAD_UP);
        boolean bucketClose = control.dpadValue(Controller.C_TWO, Dpad.DPAD_DOWN);
        double flapOpen = 0;
        if(bucketOpen){
            flapOpen = 0;
        }else if(bucketClose){
            flapOpen = 1;
        }
        servoMap.get("BucketLeft").setDesiredPosition(flapOpen);
        servoMap.get("BucketRight").setDesiredPosition(flapOpen);

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}