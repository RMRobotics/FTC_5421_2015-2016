package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Peter on 2/29/16.
 */
public class GyroTest5421 extends OpMode {

    GyroSensor gyro;
    //DcMotor mL;
    //DcMotor mR;
    Gamepad g1;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
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

        telemetry.addData("X-Y-Z-H", gyro.rawX() + "-" + gyro.rawY() + "-" + gyro.rawZ() + "-" + gyro.getHeading());
    }
}
