package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.rmrobotics.library.core.RMOpMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by School Work on 2/17/2016.
 */
public class StateAuto8121 extends RMOpMode{

    private String state = "INIT_STATE";
    private int sensorWatch = 1;

    protected Map<Integer,String> gameLocationX = new HashMap<Integer,String>();
    protected Map<Integer,String> gameLocationY = new HashMap<Integer,String>();


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
    public void init(){
        super.init();
    }

    @Override
    public void calculate(){
        switch(sensorWatch){
            case 1: //check sensors
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 21:
                break;
            case 22:
                break;
            case 23:
                break;
            case 411: //alert
                break;
            case 9: //false alarm
                break;
            case 314: //do math
                break;
        }
        switch(state){
            case "INIT_STATE": //calibrate and set telemetry for navx, optical sensors and other stuff
                break;
            case "MOVE_TO_CENTER":
                break;
            case "MOVE_TO_BEACONS":
                break;
            case "FIRST_BEACON":
                break;
            case "SECOND_BEACON":
                break;
            case "PRESS_BUTTON":
                break;
            case "DROP_CLIMBERS":
                break;
            case "FRONT_OF_MOUNTAIN":
                break;
            case "UP_THE_MOUNTAIN":
                break;
        }

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
