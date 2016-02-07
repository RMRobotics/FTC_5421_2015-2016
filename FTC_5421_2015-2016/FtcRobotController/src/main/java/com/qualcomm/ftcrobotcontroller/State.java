package com.qualcomm.ftcrobotcontroller;

import java.util.Map;

public abstract class State {


    protected boolean isComplete =  false;
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
}
