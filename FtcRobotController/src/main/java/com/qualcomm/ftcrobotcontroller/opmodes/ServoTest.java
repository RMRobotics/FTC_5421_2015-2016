package com.qualcomm.ftcrobotcontroller.opmodes;

import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;


/**
 * Created by RM Robotics on 2/12/2016.
 */
public class ServoTest extends RMOpMode {

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"servo\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "}";

    public void init() {
        super.init();
    }


    @Override
    protected void calculate() {
        boolean servoPower = control.button(Controller.C_ONE, Button.BUTTON_LB);
        boolean servoPowerBack = control.button(Controller.C_ONE, Button.BUTTON_RB);
        if(servoPower){
            servoMap.get("Servo").setDesiredPosition(1.0);

        }else if(servoPowerBack) {
            servoMap.get("Servo").setDesiredPosition(0);
        }else{
            servoMap.get("Servo").setDesiredPosition(.50);
        }

    }

    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
