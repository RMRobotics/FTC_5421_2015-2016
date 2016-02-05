package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.TimeUnit;

/**
 * Created by Simon on 2/4/2016.
 */
public class AutoOp extends LinearOpMode{

    final int MOTOR_PPR = 1680;

    State s;
    DcMotor motorLeft;
    DcMotor motorRight;
    Servo climbers;
    int stateIndex;
    STATE_TYPE stateIndexType;
    double runTime;

    private void runInit() {
        motorLeft = hardwareMap.dcMotor.get("motor1");
        motorRight = hardwareMap.dcMotor.get("motor2");
        climbers = hardwareMap.servo.get("climbers");
        s = new State(motorLeft, motorRight, climbers);
        stateIndex = 1;
        stateIndexType = STATE_TYPE.START;
        resetStartTime();
        runTime = getRuntime();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        runInit();
        addTelemetry("Waiting for start");

        waitForStart();

        while (!s.stop) {
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
                    sleep(TimeUnit.SECONDS.toMillis(s.waitTime));
                    break;
                case 6:
                    stateIndexType = STATE_TYPE.STOP;
                    s.stop = true;
                    break;
            }
            while (!s.isComplete) {
                addTelemetry("Waiting");
                waitOneFullHardwareCycle();
            }
            s.update();
            stateIndex+=1;
            addTelemetry("Done");
        }

        s.kill();
    }

    protected void addTelemetry(String key) {
        telemetry.addData(key + ": State-StateType-L-R-C-Time-isComplete", stateIndex + "-" + stateIndexType.name() + "-" + motorLeft.getCurrentPosition() + "-" + motorRight.getCurrentPosition() + "-" + climbers.getPosition() + "-" + s.time + "-" + s.isComplete);
    }

    protected int position(double rotation) {
        return (int) Math.round(rotation*MOTOR_PPR);
    }

}
