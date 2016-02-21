package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;


import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.control.Trigger;
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

        double armLeft = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double armRight = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("MotorExtendL").setDesiredPower(armLeft);
        motorMap.get("MotorExtendR").setDesiredPower(armRight);

        boolean armAngleLeft = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        double armAngleLeftBackward = control.triggerValue(Controller.C_TWO, Trigger.T_LEFT);
        double armAngleLeftPower;
        if(armAngleLeft){
            armAngleLeftPower = .5;
        }else if(armAngleLeftBackward>.1){
            armAngleLeftPower = -.5;
        }else{
            armAngleLeftPower = 0;
        }
        motorMap.get("MotorUpL").setDesiredPower(armAngleLeftPower);

        boolean armAngleRight = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        double armAngleRightBackward = control.triggerValue(Controller.C_TWO, Trigger.T_LEFT);
        double armAngleRightPower;
        if(armAngleRight){
            armAngleRightPower = .5;
        }else if(armAngleRightBackward>.1){
            armAngleRightPower = -.5;
        }else{
            armAngleRightPower = 0;
        }
        motorMap.get("MotorUpR").setDesiredPower(armAngleRightPower);

        boolean winchPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_A);
        boolean winchBack = control.buttonHeld(Controller.C_TWO, Button.BUTTON_Y);
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

        boolean bucketLeft = control.dpadValue(Controller.C_TWO, Dpad.DPAD_LEFT);
        boolean bucketRight = control.dpadValue(Controller.C_TWO, Dpad.DPAD_RIGHT);
        boolean bucketCenter = control.dpadValue(Controller.C_TWO, Dpad.DPAD_UP);
        double bucketPosition = .5;
        if(bucketLeft){
            bucketPosition = 0;
        }else if(bucketRight){
            bucketPosition = 1;
        }
        else if(bucketCenter){
            bucketPosition = .5;
        }
        servoMap.get("BucketSeeSaw").setDesiredPosition(bucketPosition);

        boolean bucketFlapL = control.buttonPressed(Controller.C_TWO, Button.BUTTON_X);
        boolean bucketFlapR = control.buttonPressed(Controller.C_TWO, Button.BUTTON_B);
        double flapOpenL = 0;
        double flapOpenR = 0;
        double flapOpenLPosition = 0;
        double flapOpenRPosition = 0;
        if(bucketFlapL){
            flapOpenL++;
        }
        if(flapOpenL%2 == 1){
            flapOpenLPosition = 1;
        }else{
            flapOpenLPosition = 0;
        }

        if(bucketFlapR){
            flapOpenR++;
        }
        if(flapOpenR%2 == 1){
            flapOpenRPosition = 1;
        }else{
            flapOpenRPosition = 0;
        }
        servoMap.get("BucketLeft").setDesiredPosition(flapOpenLPosition);
        servoMap.get("BucketRight").setDesiredPosition(flapOpenRPosition);

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}