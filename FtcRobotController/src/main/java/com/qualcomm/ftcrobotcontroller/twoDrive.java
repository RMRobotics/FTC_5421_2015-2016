package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;

/**
 * Created by Peter on 1/29/16.
 */
public class twoDrive extends RMOpMode {

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"motor1\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"motor2\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "  ],\n" +
            "}";

    @Override
    protected void calculate() {
        opType = 1;
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("motor1").setDesiredPower(leftPower);
        motorMap.get("motor2").setDesiredPower(rightPower);
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
