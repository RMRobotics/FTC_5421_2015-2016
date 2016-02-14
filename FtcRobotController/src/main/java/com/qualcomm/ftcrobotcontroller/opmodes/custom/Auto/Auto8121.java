package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.rmrobotics.library.core.RMOpMode;

import java.util.Calendar;


/**
 * Created by RM Robotics on 12/23/2015.
 */
public class Auto8121 extends RMOpMode {

    Calendar cal;

    private int state = 1;
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
            "  ],\n" +
            "  \"servos\":[\n" +
            "}";

    @Override
    public void init() {
        super.init();
        motorMap.get("MotorL").runUsingEncoders();
        motorMap.get("MotorR").runUsingEncoders();
        cal = Calendar.getInstance();
    }

    @Override
    public void calculate() {
        switch(state){
            case 1:
                motorMap.get("MotorL").setDesiredPower(.5);
                motorMap.get("MotorR").setDesiredPower(.5);
                stateOne();
                break;
            case 2:
                motorMap.get("MotorL").setDesiredPower(0);
                motorMap.get("MotorR").setDesiredPower(0);
                waitTimer();
                break;


        }

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    private void stateOne(){
        if(motorMap.get("MotorL").getCurrentPosition()>90 && motorMap.get("MotorR").getCurrentPosition()>90){
            state++;
        }
    }

    private void waitTimer(){
        cal.getTimeinMillis()
    }

}