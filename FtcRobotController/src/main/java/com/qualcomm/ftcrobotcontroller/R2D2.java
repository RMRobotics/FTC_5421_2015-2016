package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Button;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;

/**
 * Created by RM Robotics on 12/23/2015.
 */
public class R2D2 extends RMOpMode {
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
            "      \"direction\":\"FORWARD \"\n" +
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
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("DriveLeftOne").setDesiredPower(leftPower);
        motorMap.get("DriveLeftTwo").setDesiredPower(leftPower);
        motorMap.get("DriveRightOne").setDesiredPower(rightPower);
        motorMap.get("DriveRightTwo").setDesiredPower(rightPower);
        telemetry.addData("power", rightPower);

        boolean armPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_X);
        boolean armRetract = control.buttonHeld(Controller.C_TWO, Button.BUTTON_RB);
        double armPower;
        if(armPress){
            armPower = .5;
        }else if(armRetract){
            armPower = -.5;
        }else{
            armPower = 0;
        }
        motorMap.get("Arm").setDesiredPower(armPower);

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
        motorMap.get("armAngle").setDesiredPower(armAnglePower);

        boolean bucketPress = control.buttonHeld(Controller.C_ONE, Button.BUTTON_RB);
        boolean bucketPressBack = control.buttonHeld(Controller.C_ONE, Button.BUTTON_LB);
        double bucketPower;
        if(bucketPress){
            bucketPower = .5;
        }else if(bucketPressBack){
            bucketPower = -.5;
        }else{
            bucketPower = 0;
        }
        motorMap.get("Bucket").setDesiredPower(bucketPower);

        boolean bucketMoveLeft = control.buttonHeld(Controller.C_TWO, Button.BUTTON_X);
        boolean bucketMoveRight = control.buttonHeld(Controller.C_TWO, Button.BUTTON_Y);
        double bucketMovePower;
        if(bucketMoveLeft){
            bucketMovePower = .5;
        }else if(bucketMoveRight){
            bucketMovePower = -.5;
        }else{
            bucketMovePower = 0;
        }
        motorMap.get("BucketMove").setDesiredPower(bucketMovePower);
        /* Not on robot yet
        boolean harvesterForward = control.buttonHeld(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvesterReverse = control.buttonHeld(Controller.C_ONE, Button.BUTTON_RB);
        double harvesterPower;
        if(harvesterForward){
            harvesterPower = .5;
        }else if(harvesterReverse){
            harvesterPower = -.5
        }
        motorMap.get("Harvester").setDesiredPower(harvesterPower);

        //TRIGGER VALUES OR BUTTON????
       else{
            harvesterPower = 0;
        } boolean rescueArm = control.buttonHeld(Controller.C_TWO, Button.BUTTON_A);
        if(rescueArm > 1){

        }
        */

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
