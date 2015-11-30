package com.rmrobotics.library.opmodes;

import com.qualcomm.ftccommon.DbgLog; //for DbgLog
import com.rmrobotics.library.RMOpMode;

public class TestOp extends RMOpMode {

    private final String CONFIGURATION_PATH = "res/robot.json";

    //Used in case addition initialization functions need to be called
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void updateInput() {

    }

    @Override
    public void calculate() {

    }

    @Override
    public void updateHardware() {

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
