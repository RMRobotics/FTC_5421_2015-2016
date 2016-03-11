package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Peter on 2/29/16.
 */
public class GyroTest5421 extends OpMode {

    GyroSensor gyro;
    DcMotor mL;
    DcMotor mR;
    Gamepad g1;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        mL = hardwareMap.dcMotor.get("mL");
        mL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR = hardwareMap.dcMotor.get("mR");
        mR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        mR.setDirection(DcMotor.Direction.REVERSE);
        g1 = gamepad1;
    }

    public void init_loop() {
        telemetry.addData("GYRO CALIBRATING:", gyro.isCalibrating());
    }

    @Override
    public void loop() {
        double leftPower = g1.left_stick_y;
        double rightPower = g1.right_stick_y;
        if (Math.abs(45-gyro.getHeading()) > 3) {
            mL.setPower(leftPower * 0.2);
            mR.setPower(rightPower * 0.2);
        } else {
            mL.setPower(0);
            mR.setPower(0);
        }

        //telemetry.addData("X-Y-Z-H", gyro.rawX() + "-" + gyro.rawY() + "-" + gyro.rawZ() + "-" + gyro.getHeading());

        telemetry.addData("h", String.format("%03d", gyro.getHeading()));
        //telemetry.addData("t", String.format("%05d", getRuntime()));
        telemetry.addData("mL", mL.getPower());
        telemetry.addData("mR", mR.getPower());
    }
}
