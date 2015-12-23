package com.rmrobotics.library;
import com.qualcomm.ftccommon.DbgLog; //for DbgLog
import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.Motor;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.control.Controller;
/**
 * Created by RM Robotics on 12/23/2015.
 */
public class R2D2 extends RMOpMode {
    private final String CONFIGURATION_PATH = "res/robot.json";
    public R2D2() {
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void calculate() {
        control.button(Controller.C_ONE, Button.BUTTON_A);

    }

}
