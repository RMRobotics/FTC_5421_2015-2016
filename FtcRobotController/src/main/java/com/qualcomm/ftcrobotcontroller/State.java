package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

public class State {
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    boolean isComplete;
    Motor motorLeft;
    Motor motorRight;
    rServo climbers;
    int startPositionLeft;
    int startPositionRight;
    int endPositionLeft;
    int endPositionRight;
    long waitTime;

    protected enum DIRECTION {
        LEFT,
        RIGHT
    }

    public State (Motor motor1, Motor motor2, rServo climb) {
        time.reset();
        motorLeft = motor1;
        motorRight = motor2;
        climbers = climb;
        startPositionLeft = (int) motorLeft.getCurrentPosition();
        startPositionRight = (int) motorRight.getCurrentPosition();
    }

    protected void encoderStraight(int position, double power) {
        update();
        setDrivePower(power);
        setEncoder(position);
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
        motorLeft.setTargetPosition(positionLeft);
        motorRight.setTargetPosition(positionRight);
    }

    protected void update() {
        time.reset();
        startPositionLeft = (int) motorLeft.getCurrentPosition();
        startPositionRight = (int) motorRight.getCurrentPosition();
    }

    private void setMode(DcMotorController.RunMode mode) {
        switch (mode) {
            case RESET_ENCODERS:
                motorLeft.resetEncoder();
                motorRight.resetEncoder();
                break;
            case RUN_USING_ENCODERS:
                motorLeft.runUsingEncoders();
                motorRight.runUsingEncoders();
                break;
            case RUN_WITHOUT_ENCODERS:
                motorLeft.runWithoutEncoders();
                motorRight.runWithoutEncoders();
                break;
            case RUN_TO_POSITION:
                motorLeft.runToPosition();
                motorRight.runToPosition();
                break;
        }
    }

    protected boolean isComplete(FSM.STATE_TYPE type) {
        isComplete = false;
        switch (type) {
            case ENCODER_STRAIGHT:
                isComplete = (!motorLeft.isBusy() && !motorRight.isBusy());
                break;
            case ENCODER_TURN:
                isComplete = (!motorLeft.isBusy() && !motorRight.isBusy());
                break;
            case WAIT:
                isComplete = (time.time() >= waitTime);
                break;
        }
        return isComplete;
    }

}

    /*protected boolean isComplete =  false;
    protected long startTime;
    protected long curTime;
    protected long pastTime;

    public State(long currentTime){
        startTime = currentTime;
        curTime = startTime;
    }

    public State() {
        //nada
    }

    protected void updateTime(long currentTime){
        pastTime = currentTime;
        curTime = currentTime;
    }

    protected void calculate(double curPosLeft, double curPosRight) {}

    public boolean isComplete(){
        return isComplete;
    }

}

class WaitState extends State {

    long waitTime;

    public WaitState(long currentTime, long wTime){
         super(currentTime);
        waitTime =  wTime;
    }

    @Override
    protected void calculate(double curPosLeft, double curPosRight) {
        if(curTime - startTime >= waitTime){
            isComplete =  true;
        }
    }

}

class MotorState extends State {

    Motor changeMotor;
    double desiredPower;

    public MotorState(long currentTime, Motor m, double power){
        super(currentTime);
        changeMotor = m;
        desiredPower =  power;
    }

    @Override
    protected void calculate(double curPosLeft, double curPosRight) {
        changeMotor.setDesiredPower(desiredPower);
        isComplete = true;
    }
}

class EndState extends State {

    Map<String, Motor> motorMap;

    public EndState(long currentTime, Map<String, Motor> mMap){
        motorMap =  mMap;
    }

    @Override
    protected void calculate(double curPosLeft, double curPosRight) {
        for(Motor m:motorMap.values()){
            m.setDesiredPower(0.0);
        }
    }
}*/