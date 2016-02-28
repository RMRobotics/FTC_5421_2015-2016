package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;


import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.control.Trigger;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;

/**
 * Created by RM Robotics on 12/23/2015.
 */
public class TeleOp8121 extends RMOpMode {
    Motor Harvester;
    //  private final String CONFIGURATION_PATH = "res/8121.json";

    @Override
    public void init() {
        super.setTeam(8121);
        super.init();
    }

    @Override
    public void calculate() {
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("MotorL").setDesiredPower(leftPower);
        motorMap.get("MotorR").setDesiredPower(rightPower);
        telemetry.addData("power", motorMap.get("MotorL").getPower());

        double armExtend = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        motorMap.get("MotorExtendL").setDesiredPower(armExtend);
        motorMap.get("MotorExtendR").setDesiredPower(armExtend);
        motorMap.get("MotorExtendC").setDesiredPower(armExtend);



        boolean harvesterForward = control.buttonHeld(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvesterReverse = control.buttonHeld(Controller.C_ONE, Button.BUTTON_RB);
        double harvesterPower;
        if(harvesterForward){
            harvesterPower = 1;
        }else if(harvesterReverse){
            harvesterPower = -1;
        }
        else{
            harvesterPower = 0;
        }
        motorMap.get("Harvester").setDesiredPower(harvesterPower);


      /*  boolean bucketFlapL = control.buttonHeld(Controller.C_TWO, Button.BUTTON_X);
        boolean bucketFlapR = control.buttonHeld(Controller.C_TWO, Button.BUTTON_B);
        boolean bucketLOpen = false;
        boolean bucketROpen = false;
        double flapLPosition;
        double flapRPosition;

        if(bucketFlapL){
            telemetry.addData("pressR","Yes");
            bucketLOpen = !bucketLOpen;
        }
        if(bucketFlapR){
            bucketROpen = !bucketROpen;
        }

        if(bucketLOpen){
            flapLPosition = .5;
        }else{
            flapLPosition = .1;
        }

        if(bucketROpen){
            flapRPosition = .5;
        }else{
            flapRPosition = .1;
        }
        servoMap.get("BucketLeft").setDesiredPosition(flapLPosition);
        servoMap.get("BucketRight").setDesiredPosition(flapRPosition);
        telemetry.addData("flapR",flapRPosition);
        telemetry.addData("flapL",flapLPosition);*/

        boolean bucketLeft = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.buttonHeld(Controller.C_TWO, Button.BUTTON_RB);
        double flapLPosition;
        double flapRPosition;

        if(bucketLeft){
            flapLPosition = .5;
        }else{
            flapLPosition = 0;
        }
        if(bucketRight){
            flapRPosition = .5;
        }else{
            flapRPosition = 0;
        }

        servoMap.get("BucketLeft").setDesiredPosition(flapLPosition);
        servoMap.get("BucketRight").setDesiredPosition(flapRPosition);

    }
    @Override
    protected String setConfigurationPath() {
        final String CONFIGURATION_PATH ="{\n" +
                "  \"motors\":[\n" +
                "    {\n" +
                "      \"name\":\"MotorL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\":\"MotorR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"MotorExtendC\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"Harvester\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "  ],\n" +
                "  \"servos\":[\n" +
                "     {\n" +
                "      \"name\":\"BucketLeft\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"BucketRight\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    } \n" +
                "  ],\n" +
                "}";
        return CONFIGURATION_PATH;
    }

}