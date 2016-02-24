package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.ftcrobotcontroller.opmodes.sample.ColorSensorDriver;
import com.rmrobotics.library.core.RMOpMode;

import java.util.HashMap;
import java.util.Map;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;
import com.rmrobotics.library.util.StateType;

/**
 * Created by School Work on 2/17/2016.
 */
public class StateAuto8121 extends RMOpMode{

    private final int WAIT = 999;

    final int INIT_STATE = 1;
    final int MOVE_TO_CENTER = 2;
    final int MOVE_TO_BEACONS = 3;
    final int FIRST_BEACON = 4;
    final int SECOND_BEACON = 5;
    final int PRESS_BUTTON = 6;
    final int DROP_CLIMBERS = 7;
    final int FRONT_OF_MOUNTAIN = 8;
    final int UP_THE_MOUNTAIN = 9;

    Motor motorL;
    Motor motorR;
    Motor motorExtendL;
    Motor motorExtendR;
    Motor motorUpL;
    Motor motorUpR;
    Motor winch;
    Motor harvester;
    rServo bucketSeeSaw;
    rServo bucketRight;
    rServo bucketLeft;
    rServo latch1;
    rServo latch2;


    private int state = 0;
    StateType pastState;
    private int sensorWatch = 1;
    private String State_Index = "INIT_STATE";

    ColorSensor colorSensor;
    public String teamColor = "red";
    public DeviceInterfaceModule cdim;
    private AHRS navx_device;
    private final int NAVX_DIM_I2C_PORT = 0;

    protected Map<Integer,String> gameLocationX = new HashMap<Integer,String>();
    protected Map<Integer,String> gameLocationY = new HashMap<Integer,String>();


    @Override
    public void init(){
        super.init();
        colorSensor = hardwareMap.colorSensor.get("cS");
        colorSensor.enableLed(false);
        navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kQuatAndRawData);

        motorL = motorMap.get("MotorL");
        motorR = motorMap.get("MotorR");
        motorExtendL = motorMap.get("MotorExtendL");
        motorExtendR = motorMap.get("MotorExtendR");
        motorUpL = motorMap.get("MotorUpL");
        motorUpR = motorMap.get("MotorUpR");
        winch = motorMap.get("winch");
        harvester = motorMap.get("Harvester");
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
           case WAIT:
               switch(pastState){
                   case TO_CENTER:
                       break;
                   case TO_BEACONS:
                       break;
                   case FIRST_BEACON:
                       break;
                   case SECOND_BEACON:
                       break;
                   case PRESS_BUTTON:
                       break;
                   case DROP_CLIMBERS:
                       break;
                   case TO_MOUNTAINFRONT:
                       break;
                   case UP_MOUNTAIN:
                       break;
               }
               break;
        }

    }

    public void InitMove(){

    }
    boolean initMovement(){
        boolean movement=false;
        return movement;
    }

public void ToCenter(){

}
    boolean isAtCenter(){
        boolean centered=false;
        return centered;

    }
























































































































































































































































































































































































//THE UNDERWORLD
    @Override
    protected String setConfigurationPath() {
        final String CONFIGURATION_PATH = "{\n" +
                "  \"motors\":[\n" +
                "    {\n" +
                "      \"name\":\"MotorL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"MotorUpL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"MotorUpR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"Winch\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"Harvester\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\"\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "  ],\n" +
                "  \"servos\":[\n" +
                "    {\n" +
                "      \"name\":\"BucketSeeSaw\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.01,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"BucketLeft\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":1.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"BucketRight\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":1.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"Latch1\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"Latch2\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":.5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return CONFIGURATION_PATH;
    }

}
