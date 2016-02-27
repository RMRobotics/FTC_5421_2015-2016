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
        boolean bucketLOpen = false;
        boolean bucketROpen = false;
        double flapLPosition;
        double flapRPosition;

        if(bucketFlapL){
            bucketLOpen = !bucketLOpen;
        }
        if(bucketFlapR){
            bucketROpen = !bucketROpen;
        }

        if(bucketLOpen){
            flapLPosition = .1;
        }else{
            flapLPosition = .9;
        }

        if(bucketROpen){
            flapRPosition = .1;
        }else{
            flapRPosition = .9;
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
                "    {\n" +
                "      \"name\":\"BucketSeeSaw\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    },\n" +
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