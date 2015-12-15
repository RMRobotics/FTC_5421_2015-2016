package com.rmrobotics.library;

import com.qualcomm.ftccommon.DbgLog; //for DbgLog
import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.Motor;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.control.Controller;

public class TestOp extends RMOpMode {

    private final String CONFIGURATION_PATH = "res/robot.json";

    //Used in case addition initialization functions need to be called
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void calculate() {
        control.button(Controller.C_ONE, Button.BUTTON_A);

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
