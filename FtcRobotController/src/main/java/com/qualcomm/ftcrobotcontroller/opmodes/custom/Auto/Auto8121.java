package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.util.StateType;

import java.util.Calendar;


/**
 * Created by RM Robotics on 12/23/2015.
 */
public class Auto8121 extends RMOpMode {


    private int state = 1;
    StateType curState = StateType.DRIVE_FORWARD;
    ElapsedTime AutoTimer;

    private final int WAIT = 8121;


    private final String CONFIGURATION_PATH = "";


    @Override
    public void init() {
        super.setTeam(8121);
        super.init();
        AutoTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        AutoTimer.reset();
        //motorMap.get("MotorL").setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
       // motorMap.get("MotorR").setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void calculate() {
        telemetry.addData("State",state);
        telemetry.addData("Wait",curState);
        telemetry.addData("Time",AutoTimer.time());
        switch(state){
            case 1:
                motorMap.get("MotorL").setDesiredPower(.5);
                motorMap.get("MotorR").setDesiredPower(.5);
                curState = StateType.DRIVE_FORWARD;
                state = WAIT;
                break;
            case 2:
                motorMap.get("MotorL").setDesiredPower(.5);
                motorMap.get("MotorR").setDesiredPower(0);
                curState = StateType.TURN;
                state = WAIT;
                break;
            case 3:
                motorMap.get("MotorL").setDesiredPower(1);
                motorMap.get("MotorR").setDesiredPower(1);
                curState = StateType.UP_MOUNTAIN;
                state = WAIT;
                break;
            case 4:
                motorMap.get("MotorL").setDesiredPower(0);
                motorMap.get("MotorR").setDesiredPower(0);
            case WAIT:
                switch(curState){
                    case DRIVE_FORWARD:
                        stateOne();
                        break;
                    case TURN:
                        stateTwo();
                        break;
                    case UP_MOUNTAIN:
                        stateThree();
                        break;
                }


        }

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    private void stateOne(){
        if(AutoTimer.time() >5000){
            state = 2;
        }
    }

    private void stateTwo(){
        if(AutoTimer.time() >20000){
            state = 3;
        }
    }

    private void stateThree(){
        if(AutoTimer.time() >25000){
            state = 4;
        }
    }


    private void waitTimer(){
        if(AutoTimer.time() >10000){
            state = 1;
        }
    }

}