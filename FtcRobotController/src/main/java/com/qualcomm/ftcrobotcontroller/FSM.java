package com.qualcomm.ftcrobotcontroller;

/**
 * Created by Simon on 2/2/2016.
 */
public class FSM extends RMOpMode{
    protected Motor motorLeft;
    protected Motor motorRight;
    protected rServo climbers;
    private int stateIndex;
    private STATE_TYPE stateIndexType;
    protected State s;
    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"motor1\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"motor2\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.6,\n" +
            "    }\n" +
            "  ],\n" +
            "}";

    protected enum STATE_TYPE {
        INIT,
        ENCODER_STRAIGHT,
        ENCODER_TURN,
        SERVO,
        WAIT,
        STOP
    }

    @Override
    public void init() {
        super.init();
        motorLeft = motorMap.get("motor1");
        motorRight = motorMap.get("motor2");
        climbers = servoMap.get("Climbers");
        s = new State(motorLeft, motorRight, climbers);
        stateIndex = 1;
        stateIndexType = STATE_TYPE.INIT;
        telemetry.addData("State-Time", stateIndex + "-" + s.time);
    }

    @Override
    public void loop() {
        switch (stateIndex) {
            case 1:
                s.encoderStraight(position(2), 1.0);
                stateIndexType = STATE_TYPE.ENCODER_STRAIGHT;
                break;
            case 2:
                s.encoderTurn(State.DIRECTION.LEFT, position(2), 1);
                stateIndexType = STATE_TYPE.ENCODER_TURN;
                break;
            case 3:
                s.encoderStraight(position(2), 1.0);
                stateIndexType = STATE_TYPE.ENCODER_STRAIGHT;
                break;
            case 4:
                s.encoderTurn(State.DIRECTION.RIGHT, position(2), 1);
                stateIndexType = STATE_TYPE.ENCODER_TURN;
                break;
            case 5:
                s.waitTime(3);
                stateIndexType = STATE_TYPE.WAIT;
                break;
        }
        while (!s.isComplete) {
            s.isComplete(stateIndexType);
        }
        s.update();
        stateIndex++;
    }

    @Override
    protected void calculate() {
        
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    protected int position(double rotation) {
        return (int) Math.round(rotation*1120);
    }

}
