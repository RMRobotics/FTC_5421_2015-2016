package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.ftcrobotcontroller.opmodes.sample.ColorSensorDriver;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.rmrobotics.library.core.RMOpMode;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.rmrobotics.library.util.StateType;

/**
 * Created by School Work on 2/17/2016.
 */
public class StateAutoMountain8121 extends RMOpMode{

    private final int WAIT = 999;

    final int INIT_STATE = 1;
    final int MOVE_TO_CENTER = 2;
    final int UP_THE_MOUNTAIN = 3;
    final int TURN = 303;

    Motor motorL;
    Motor motorR;


    private int state = 3;
    private int prevState = 3;
    StateType curState;
    private int sensorWatch = 1;
    private String State_Index = "INIT_STATE";
    public boolean atTarPos = false;

    ColorSensor colorSensor;
    public String teamColor = "red";
    GyroSensor sensorGyro;
    int heading = 0;
    //public DeviceInterfaceModule cdim;
    //private AHRS navx_device;
    //private final int NAVX_DIM_I2C_PORT = 0;

    protected Map<Integer,String> gameLocationX = new HashMap<Integer,String>();
    protected Map<Integer,String> gameLocationY = new HashMap<Integer,String>();
    public double CUR_X_ROBOT = 0;
    public double CUR_Y_ROBOT = 0;
    public double LAST_X_ROBOT = 0;
    public double LAST_Y_ROBOT = 0;
    public double TARGET_X;
    public double TARGET_Y;
    public double boardLength = 100000;

    public int tarAngle = 0;
    public int tarDistance = 0;




    @Override
    public void init(){
        super.setTeam(8121);
        super.init();
        colorSensor = hardwareMap.colorSensor.get("cS");
        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        colorSensor.enableLed(false);
        sensorGyro.calibrate();

        // navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
        //       NAVX_DIM_I2C_PORT,
        //     AHRS.DeviceDataType.kQuatAndRawData);

        motorL = motorMap.get("MotorL");
        motorR = motorMap.get("MotorR");
        sensorGyro.resetZAxisIntegrator();

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
        //boolean connected = navx_device.isConnected();
        heading = sensorGyro.getHeading();
        CUR_X_ROBOT = updateXPosition();
        CUR_Y_ROBOT = updateYPosition();
        tarAngle = TargetAngle();
        tarDistance = TargetDistance();
        if(Math.abs(tarAngle-heading) > 5){
            OffMotors();
            prevState = state;
            state = TURN;
        }else if (state == TURN){
            state = prevState;
        }
        /*
        switch(sensorWatch){
            case 1: //gyro = 0
                break;
            case 11: // gyro = 90
                break;
            case 12: //blue
                break;
            case 13: //red
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
        }*/
        switch(state){
            case INIT_STATE:
                TARGET_X = 0;
                TARGET_Y = Math.sqrt(2)*boardLength*2.1/6;
                state = MOVE_TO_CENTER;
            case MOVE_TO_CENTER:
                motorL.setTargetPosition(tarDistance);
                motorR.setTargetPosition(tarDistance);
                motorL.setDesiredPower(.7);
                motorR.setDesiredPower(.7);
                curState = StateType.TO_CENTER;
                prevState = state;
                state = WAIT;
                break;
            case UP_THE_MOUNTAIN:
                motorL.setTargetPosition(tarDistance);
                motorR.setTargetPosition(tarDistance);
                motorL.setDesiredPower(1);
                motorR.setDesiredPower(1);
                curState = StateType.UP_MOUNTAIN;
                prevState = state;
                state = WAIT;
                break;
            case TURN: //ADD CW OR CCW BASED ON NEGATIVE ANGLES TO REDUCE TIME
                motorR.setTargetPosition((int)motorR.getCurrentPosition()+100000);
                motorL.setTargetPosition((int)motorL.getCurrentPosition());
                motorR.setDesiredPower(.9);
                motorL.setDesiredPower(0);
                break;
            case WAIT:
                atTarPos = TargetPositionCheck();
                if(atTarPos){
                    state = prevState + 1;
                    OffMotors();
                    atTarPos = !atTarPos;
                    switch(curState){
                        case TO_CENTER:
                            TARGET_Y = Math.sqrt(2)*boardLength*2.1/6;
                            TARGET_X = Math.sqrt(2)*boardLength*1.5/6;
                            break;
                        case UP_MOUNTAIN:
                            break;
                    }
                }
                break;
        }

    }


    public double updateXPosition(){
        double newX = CUR_X_ROBOT + (motorL.getCurrentPosition()-CUR_X_ROBOT)*(Math.sin(Math.toRadians(heading-tarAngle)));
        return newX;
    }

    public double updateYPosition(){
        double newY = CUR_Y_ROBOT + (motorL.getCurrentPosition()-CUR_Y_ROBOT)*(Math.sin(Math.toRadians(heading-tarAngle)));
        return newY;
    }

    public int TargetAngle(){
        int angle = (int)Math.atan((TARGET_X-CUR_X_ROBOT)/(TARGET_Y-CUR_Y_ROBOT));
        return angle;
    }

    public int TargetDistance(){
        int distance = (int)Math.sqrt(Math.pow(TARGET_X - CUR_X_ROBOT, 2) / Math.pow(TARGET_Y - CUR_Y_ROBOT, 2));
        return distance;
    }

    public boolean TargetPositionCheck(){
        if(motorL.getCurrentPosition() - motorL.getTargetPosition() >= 0 && motorR.getCurrentPosition() - motorR.getTargetPosition() >= 0){
            return true;
        }else{
            return false;
        }
    }

    public void OffMotors(){
        motorL.setDesiredPower(0);
        motorR.setDesiredPower(0);
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

