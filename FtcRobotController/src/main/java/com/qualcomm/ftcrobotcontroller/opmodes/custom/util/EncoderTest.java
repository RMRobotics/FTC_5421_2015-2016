
package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;

public class EncoderTest extends RMOpMode {
    DcMotor mL;
    DcMotor mR;
    DcMotor eL;
    DcMotor eR;
    DcMotor h;

    boolean hP = false;

    /*private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"motor\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "  ],\n" +
            "}";*/

    public void init() {
        mL = hardwareMap.dcMotor.get("motorLeft");
        mL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR = hardwareMap.dcMotor.get("motorRight");
        mR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR.setDirection(DcMotor.Direction.REVERSE);
        eL = hardwareMap.dcMotor.get("extendLeft");
        eL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        eR = hardwareMap.dcMotor.get("extendRight");
        eR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        eR.setDirection(DcMotor.Direction.REVERSE);
        h = hardwareMap.dcMotor.get("harvester");
    }

    protected void calculate() {
        boolean hTog = control.button(Controller.C_ONE, Button.BUTTON_A);
        if (hTog) {
            hP = !hP;
        }
        if (hP) {
            h.setPower(1.0);
        } else {
            h.setPower(0);
        }

        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        mL.setPower(leftPower);
        mR.setPower(rightPower);

        boolean extendUp = control.button(Controller.C_TWO, Button.BUTTON_A);
        boolean extendDown = control.button(Controller.C_TWO, Button.BUTTON_B);
        if (extendUp) {
            mL.setPower(0.3);
            mR.setPower(0.3);
        } else if (extendDown) {
            mL.setPower(-0.3);
            mR.setPower(-0.3);
        } else {
            mL.setPower(0);
            mR.setPower(0);
        }

    }

    @Override
    protected String setConfigurationPath() {
        return null;
    }
}