package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Peter on 2/29/16.
 */
public class ColorSensorTest5421 extends OpMode {

    ColorSensor cs;
    //DcMotor mL;
    //DcMotor mR;
    Gamepad g1;

    @Override
    public void init() {
        cs = hardwareMap.colorSensor.get("cs");
        //mL = hardwareMap.dcMotor.get("mL");
        //mR = hardwareMap.dcMotor.get("mR");
        //mR.setDirection(DcMotor.Direction.REVERSE);
        g1 = gamepad1;
    }

    @Override
    public void loop() {
        //double leftPower = g1.left_stick_y;
        //double rightPower = g1.right_stick_y;
        //mL.setPower(leftPower);
        //mR.setPower(rightPower);

        telemetry.addData("R-G-B-A-H", cs.red() + "-" + cs.green() + "-" + cs.blue() + "-" + cs.alpha() + "-" + cs.argb());
    }
}
