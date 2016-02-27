package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;

/**
 * Created by RM Robotics on 2/27/2016.
 */
public class BoardTest extends RMOpMode{

    TouchSensor touchSensor;
    public double encL;
    public double encR;

    @Override
    public void init(){
        super.setTeam(8121);
        super.init();
        touchSensor = hardwareMap.touchSensor.get("sensor_touch");

        for (Motor m : motorMap.values()) {
            m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            m.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }

    @Override
    public void calculate(){
        for (Motor m : motorMap.values()) {
            m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }

        encL = motorMap.get("MotorL").getCurrentPosition();
        encR = motorMap.get("MotorR").getCurrentPosition();
        if(touchSensor.isPressed()){
            motorMap.get("MotorL").setDesiredPower(0);
            motorMap.get("MotorR").setDesiredPower(0);
        }else{
            motorMap.get("MotorL").setDesiredPower(.7);
            motorMap.get("MotorR").setDesiredPower(.7);
        }
        telemetry.addData("Left",encL);
        telemetry.addData("Right",encR);

    }

    @Override
    protected String setConfigurationPath() {
        final String CONFIGURATION_PATH = "{\n" +
                "  \"motors\":[\n" +
                "    {\n" +
                "      \"name\":\"MotorL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
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
                "      \"motorType\":\"TETRIX\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"servos\":[\n" +
                "     {\n" +
                "      \"name\":\"BucketLeft\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.1\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"BucketRight\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.1\n" +
                "    } \n" +
                "  ],\n" +
                "}";
        return CONFIGURATION_PATH;
    }
}
