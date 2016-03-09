package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Peter on 3/9/16.
 */
public class DemoBot extends OpMode {

    DcMotor mL;
    DcMotor mR;

    @Override
    public void init() {
        mL = hardwareMap.dcMotor.get("mL");
        mL.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        mR = hardwareMap.dcMotor.get("mR");
        mR.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        mR.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double leftPower = gamepad1.left_stick_y;
        double rightPower = gamepad1.right_stick_y;
        mL.setPower(leftPower);
        mR.setPower(rightPower);
    }
}
