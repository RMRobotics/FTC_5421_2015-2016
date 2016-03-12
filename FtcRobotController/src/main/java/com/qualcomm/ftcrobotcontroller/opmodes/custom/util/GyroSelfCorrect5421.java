package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Simon on 3/11/2016.
 */
public class GyroSelfCorrect5421 extends OpMode {

    GyroSensor gyro;
    DcMotor mL;
    DcMotor mR;
    String skew;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        mL = hardwareMap.dcMotor.get("mL");
        mL.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        mL.setDirection(DcMotor.Direction.REVERSE);
        mR = hardwareMap.dcMotor.get("mR");
        mR.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public void init_loop() {
        telemetry.addData("CALIBRATION DONE:", !gyro.isCalibrating());
    }

    @Override
    public void loop() {
        if (gyro.getHeading() > 2 && gyro.getHeading() < 180) {
            drive(0.2, 0.5);
            skew = "RIGHT";
        } else if (gyro.getHeading() < 358 && gyro.getHeading() > 180) {
            drive(0.5, 0.2);
            skew = "LEFT";
        } else {
            drive(0.5);
            skew = "STRAIGHT";
        }
        addTelemetry();
    }

    private void drive(double p) {
        mL.setPower(p);
        mR.setPower(p);
    }

    private void drive(double p1, double p2) {
        mL.setPower(p1);
        mR.setPower(p2);
    }

    private void addTelemetry() {
        telemetry.addData("SKEW", skew);
        telemetry.addData("h", String.format("%03d", gyro.getHeading()));
        telemetry.addData("mL", mL.getPower());
        telemetry.addData("mR", mR.getPower());
    }
}
