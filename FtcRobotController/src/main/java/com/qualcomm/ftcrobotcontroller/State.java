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
    long waitTime;

    protected enum DIRECTION {
        LEFT,
        RIGHT
    }

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

    protected void encoderStraight(int position, double power) {
        update();
        setDrivePower(power);
        setEncoder(position);
        updateEnd(startPositionLeft + position, startPositionRight + position);
        setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    protected void encoderTurn(DIRECTION direction, int position, double power) {
        update();
        switch (direction) {
            default:
            case LEFT:
                setDrivePower(0, power);
                updateEnd(startPositionLeft, startPositionRight + position);
                setEncoder(endPositionLeft, endPositionRight);
                break;
            case RIGHT:
                setDrivePower(power, 0);
                updateEnd(startPositionLeft + position, startPositionRight);
                setEncoder(endPositionLeft, endPositionRight);
                break;
        }
    }

    protected void waitTime(long wait) {
        waitTime = wait;
        update();
    }

    private void updateEnd(int endLeft, int endRight) {
        endPositionLeft = endLeft;
        endPositionRight = endRight;
    }

    private void setDrivePower(double power) {
        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    private void setDrivePower(double powerLeft, double powerRight) {
        motorLeft.setPower(powerLeft);
        motorRight.setPower(powerRight);
    }

    private void setEncoder(int position) {
        motorLeft.setTargetPosition(position);
        motorRight.setTargetPosition(position);
    }

    private void setEncoder(int positionLeft, int positionRight) {
        motorLeft.setTargetPosition(startPositionLeft + positionLeft);
        motorRight.setTargetPosition(startPositionRight + positionRight);
    }

    protected void update() {
        time.reset();
        startPositionLeft = motorLeft.getCurrentPosition();
        startPositionRight = motorRight.getCurrentPosition();
    }

    private void setMode(DcMotorController.RunMode mode) {
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

    protected boolean isComplete(STATE_TYPE type) {
        isComplete = false;
        switch (type) {
            case ENCODER_STRAIGHT:
                isComplete = ((Math.abs(endPositionLeft - motorLeft.getCurrentPosition())) < 10 && (Math.abs(endPositionRight - motorRight.getCurrentPosition())) < 10);
                break;
            case ENCODER_TURN:
                isComplete = ((Math.abs(endPositionLeft) - motorLeft.getCurrentPosition()) < 10 && (Math.abs(endPositionRight - motorRight.getCurrentPosition()) < 10));
                break;
            case WAIT:
                isComplete = true;
                break;
            default:
                isComplete = false;
        }
        return isComplete;
    }

}