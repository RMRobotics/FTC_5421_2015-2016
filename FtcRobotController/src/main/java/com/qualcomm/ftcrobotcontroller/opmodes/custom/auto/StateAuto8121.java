package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.ftcrobotcontroller.opmodes.sample.ColorSensorDriver;
import com.rmrobotics.library.core.RMOpMode;

import java.util.HashMap;
import java.util.Map;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;

/**
 * Created by School Work on 2/17/2016.
 */
public class StateAuto8121 extends RMOpMode{

    final int INIT_STATE = 1;
    final int MOVE_TO_CENTER = 2;
    final int MOVE_TO_BEACONS = 3;
    final int FIRST_BEACON = 4;
    final int SECOND_BEACON = 5;
    final int PRESS_BUTTON = 6;
    final int DROP_CLIMBERS = 7;
    final int FRONT_OF_MOUNTAIN = 8;
    final int UP_THE_MOUNTAIN = 9;


    /* switch(state){
                State_Index = "INIT_STATE": //calibrate and set telemetry for navx, optical sensors and other stuff
                break;
                State_Index = "MOVE_TO_CENTER":
                break;
                State_Index = "MOVE_TO_BEACONS":
                break;
                State_Index = "FIRST_BEACON":
                break;
                State_Index = "SECOND_BEACON":
                break;
                State_Index = "PRESS_BUTTON":
                break;
                State_Index = "DROP_CLIMBERS":
                break;
                State_Index = "FRONT_OF_MOUNTAIN":
                break;
                State_Index = "UP_THE_MOUNTAIN":
                break;
        }*/

    private int state = 0;
    private int sensorWatch = 1;
    private String State_Index = "INIT_STATE";

    ColorSensor colorSensor;
    public DeviceInterfaceModule cdim;
    private AHRS navx_device;
    private final int NAVX_DIM_I2C_PORT = 0;

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
        colorSensor = hardwareMap.colorSensor.get("cS");
        colorSensor.enableLed(false);
        navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kQuatAndRawData);
    }

    @Override
    public void calculate(){
        boolean connected = navx_device.isConnected();
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
           case INIT_STATE: //calibrate and set telemetry for navx, optical sensors and other stuff
                break;
           case MOVE_TO_CENTER:
                break;
           case MOVE_TO_BEACONS:
                break;
           case FIRST_BEACON:
                break;
           case SECOND_BEACON:
                break;
           case PRESS_BUTTON:
                break;
           case DROP_CLIMBERS:
                break;
           case FRONT_OF_MOUNTAIN:
                break;
           case UP_THE_MOUNTAIN:
                break;
        }

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
