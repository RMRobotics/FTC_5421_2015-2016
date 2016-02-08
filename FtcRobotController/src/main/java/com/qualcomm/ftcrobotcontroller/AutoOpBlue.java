package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Simon on 2/4/2016.
 */
public class AutoOpBlue extends LinearOpMode{

    DcMotor motorLeft;
    DcMotor motorRight;

    private void runInit() {
        motorRight = hardwareMap.dcMotor.get("DriveLeftOne");
        motorLeft = hardwareMap.dcMotor.get("DriveRightOne");
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        resetStartTime();
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        addTelemetry();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        runInit();
        waitForStart();

        addTelemetry();
        driveStraight(1.0);
        addTelemetry();
        sleep(3000);
        addTelemetry();
        kill();
        addTelemetry();
        sleep(500);
        addTelemetry();
        driveRight(1.0);
        addTelemetry();
        sleep(1800);
        addTelemetry();
        kill();
        addTelemetry();
        sleep(1000);
        addTelemetry();
        driveStraight(1.0);
        addTelemetry();
        sleep(5000);
        addTelemetry();

        kill();

        telemetry.addData("DONE", "SWAG");

    }

    protected void addTelemetry() {
        telemetry.addData("RunTime-L-R", getRuntime() + "-" + motorLeft.getPower() + "-" + motorRight.getPower());
    }

    private void kill() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    private void driveStraight(double power) {
        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    private void driveLeft(double power) {
        motorRight.setPower(power);
    }

    private void driveRight(double power) {
        motorLeft.setPower(power);
    }
}
