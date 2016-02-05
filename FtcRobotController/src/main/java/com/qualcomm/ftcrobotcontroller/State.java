package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class State {
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    boolean isComplete;
    boolean stop;
    DcMotor motorLeft;
    DcMotor motorRight;
    Servo climbers;
    int startPositionLeft;
    int startPositionRight;
    int endPositionLeft;
    int endPositionRight;

    public State (DcMotor motor1, DcMotor motor2, Servo climb) {
        time.reset();
        motorLeft = motor1;
        motorRight = motor2;
        climbers = climb;
        startPositionLeft = motorLeft.getCurrentPosition();
        startPositionRight = motorRight.getCurrentPosition();
        isComplete = false;
        stop = false;
    }

    protected void encoder(int positionLeft, int positionRight, double powerLeft, double powerRight) {
        update();
        setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setTargetPosition(startPositionLeft + positionLeft);
        motorRight.setTargetPosition(startPositionRight + positionRight);
        setDrivePower(powerLeft, powerRight);
        updateEnd();
    }

    private void updateEnd() {
        endPositionLeft = motorLeft.getTargetPosition();
        endPositionRight = motorRight.getTargetPosition();
    }

    private void setDrivePower(double power) {
        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    private void setDrivePower(double powerLeft, double powerRight) {
        motorLeft.setPower(powerLeft);
        motorRight.setPower(powerRight);
    }

    protected void update() {
        time.reset();
        startPositionLeft = motorLeft.getCurrentPosition();
        startPositionRight = motorRight.getCurrentPosition();
    }

    protected void setMode(DcMotorController.RunMode mode) {
        switch (mode) {
            case RESET_ENCODERS:
                motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                break;
            case RUN_USING_ENCODERS:
                motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                break;
            case RUN_WITHOUT_ENCODERS:
                motorLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                motorRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                break;
            case RUN_TO_POSITION:
                motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                break;
        }
    }

    protected void kill(){
        setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        setDrivePower(0, 0);
    }

    protected void reset() {
        setMode(DcMotorController.RunMode.RESET_ENCODERS);
        startPositionLeft = 0;
        startPositionRight = 0;
        endPositionLeft = 0;
        endPositionRight = 0;
    }

    protected boolean isComplete(STATE_TYPE type) {
        isComplete = false;
        switch (type) {
            case ENCODER:
                return (Math.abs(motorLeft.getTargetPosition() - motorLeft.getCurrentPosition())) < 10 && (Math.abs(motorRight.getTargetPosition() - motorRight.getCurrentPosition()) < 10);
            case WAIT:
                return (true);
            default:
                return false;
        }
    }

}