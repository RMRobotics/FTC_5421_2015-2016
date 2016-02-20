
package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;

public class EncoderTest extends RMOpMode {
/*    DcMotor mL;
    DcMotor mR;
    DcMotor eL;
    DcMotor eR;
    DcMotor h;*/

    Motor mL;
    Motor mR;
    Motor eL;
    Motor eR;
    Motor h;

    boolean hP = false;

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"mL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "      \"motorType\":\"NVRST_40\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"mR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "      \"motorType\":\"NVRST_40\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"eL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" + "{\n" +
            "      \"name\":\"eR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" + "{\n" +
            "      \"name\":\"h\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "      \"motorType\":\"NVRST_20\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "  ],\n" +
            "}";

    public void init() {
        super.init();
        /*mL = hardwareMap.dcMotor.get("mL");
        mL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR = hardwareMap.dcMotor.get("mR");
        mR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR.setDirection(DcMotor.Direction.REVERSE);
        eL = hardwareMap.dcMotor.get("eL");
        eL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        eR = hardwareMap.dcMotor.get("eR");
        eR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        eR.setDirection(DcMotor.Direction.REVERSE);
        h = hardwareMap.dcMotor.get("h");*/
        mL = motorMap.get("mL");
        mR = motorMap.get("mR");
        eL = motorMap.get("eL");
        eR = motorMap.get("eR");
        h = motorMap.get("h");
    }

    protected void calculate() {
        for (Motor m : motorMap.values()) {
            if (m.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
                m.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
        boolean hTog = control.button(Controller.C_ONE, Button.BUTTON_A);
        if (hTog) {
            hP = !hP;
        }
        if (hP) {
            h.setDesiredPower(1.0);
        } else {
            h.setDesiredPower(0);
        }

        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        mL.setDesiredPower(leftPower);
        mR.setDesiredPower(rightPower);

        boolean extendUp = control.button(Controller.C_TWO, Button.BUTTON_A);
        boolean extendDown = control.button(Controller.C_TWO, Button.BUTTON_B);
        if (extendUp) {
            eL.setDesiredPower(0.2);
            eR.setDesiredPower(0.2);
        } else if (extendDown) {
            eL.setDesiredPower(-0.2);
            eR.setDesiredPower(-0.2);
        } else {
            eL.setDesiredPower(0);
            eR.setDesiredPower(0);
        }

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}