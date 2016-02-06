package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.TimeUnit;

/**
 * Created by Simon on 2/4/2016.
 */
public class AutoOpRed extends LinearOpMode{

    //final int MOTOR_PPR = 1120;
    //final double CLIMBERS_INIT = 1.0;
    //final double CLIMBERS_SCORE = 0.0;

    DcMotor motorLeft;
    DcMotor motorRight;
    //DcMotor harvester;
    //Servo climbers;
    //double runTime;

    private void runInit() {
        motorRight = hardwareMap.dcMotor.get("DriveLeftOne");
        motorLeft = hardwareMap.dcMotor.get("DriveRightOne");
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        //climbers = hardwareMap.servo.get("Climbers");
        //harvester = hardwareMap.dcMotor.get("Harvester");
        resetStartTime();
        //runTime = getRuntime();
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        addTelemetry();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        runInit();
        //addTelemetry("Waiting for start");
        waitForStart();

        //runStart();
        addTelemetry();
        driveStraight(1.0);
        addTelemetry();
        sleep(3000);
        addTelemetry();
        kill();
        addTelemetry();
        sleep(1000);
        addTelemetry();
        driveLeft(1.0);
        addTelemetry();
        sleep(1000);
        addTelemetry();
        kill();
        addTelemetry();
        sleep(1000);
        addTelemetry();
        driveStraight(1.0);
        addTelemetry();
        sleep(1000);
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
        //harvester.setPower(0);
        //climbers.setPosition(CLIMBERS_INIT);
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
