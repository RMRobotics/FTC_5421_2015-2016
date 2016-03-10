package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Peter on 2/29/16.
 */
public class GyroTest5421 extends OpMode {

    GyroSensor gyro;
    //DcMotor mL;
    //DcMotor mR;
    //Gamepad g1;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        //mL = hardwareMap.dcMotor.get("mL");
        //mL.setDirection(DcMotor.Direction.REVERSE);
        //mR = hardwareMap.dcMotor.get("mR");
        //g1 = gamepad1;
    }

    public void init_loop() {
        telemetry.addData("GYRO CALIBRATING:", gyro.isCalibrating());
    }

    @Override
    public void loop() {
        //double leftPower = g1.left_stick_y;
        //double rightPower = g1.right_stick_y;
        //mL.setPower(leftPower*0.5);
        //mR.setPower(rightPower*0.5);

        //telemetry.addData("X-Y-Z-H", gyro.rawX() + "-" + gyro.rawY() + "-" + gyro.rawZ() + "-" + gyro.getHeading());

        telemetry.addData("1. x", String.format("%03d", gyro.rawX()));
        telemetry.addData("2. y", String.format("%03d", gyro.rawY()));
        telemetry.addData("3. z", String.format("%03d", gyro.rawZ()));
        telemetry.addData("4. h", String.format("%03d", gyro.getHeading()));
        telemetry.addData("5. t", String.format("%05d", getRuntime()));
    }
}
