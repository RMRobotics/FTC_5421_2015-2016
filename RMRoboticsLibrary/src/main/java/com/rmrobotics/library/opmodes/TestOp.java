package com.rmrobotics.library.opmodes;

import com.qualcomm.ftccommon.DbgLog; //for DbgLog
import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.Motor;
import com.rmrobotics.library.control.Control;

public class TestOp extends RMOpMode {

    private final String CONFIGURATION_PATH = "res/robot.json";

    //Used in case addition initialization functions need to be called
    @Override
    public void init() {
        super.init();
    }
    double hi = 5;
    Motor c = new Motor("motor1","reverse", 0, 1);

    @Override
    public void updateInput() {




    }

    @Override
    public void calculate() {

    }

    @Override
    public void updateHardware() {
        Motor.
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
