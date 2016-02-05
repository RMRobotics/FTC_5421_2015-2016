package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
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
        s.reset();
        runTime = getRuntime();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //runInit();
        //addTelemetry("Waiting for start");

        motorLeft = hardwareMap.dcMotor.get("motor1");
        motorRight = hardwareMap.dcMotor.get("motor2");
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorLeft.setTargetPosition(motorLeft.getCurrentPosition() + 10000);
        motorRight.setTargetPosition(motorRight.getCurrentPosition() + 10000);
        motorLeft.setPower(1.0);
        motorRight.setPower(1.0);

        sleep(100);
        addTelemetry();
        while (!(Math.abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) < 10) && !(Math.abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) < 10)) {
            telemetry.addData("KEY", motorLeft.getCurrentPosition() + "-" + motorLeft.getTargetPosition() + "-" + motorRight.getCurrentPosition() + "-" + motorRight.getTargetPosition());
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(1000);

        motorLeft.setTargetPosition(motorLeft.getCurrentPosition() + 10000);
        motorRight.setTargetPosition(motorRight.getCurrentPosition() + 10000);
        motorLeft.setPower(1.0);
        motorRight.setPower(1.0);

        sleep(100);
        addTelemetry();
        while (!(Math.abs(motorLeft.getCurrentPosition() - motorLeft.getTargetPosition()) < 10) && !(Math.abs(motorRight.getCurrentPosition() - motorRight.getTargetPosition()) < 10)) {
            telemetry.addData("KEY", motorLeft.getCurrentPosition() + "-" + motorLeft.getTargetPosition() + "-" + motorRight.getCurrentPosition() + "-" + motorRight.getTargetPosition());
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        telemetry.addData("DONE", "SWAG");

        /*s.encoder(position(2), position(2), 1.0, 1.0);
        stateIndexType = STATE_TYPE.ENCODER;
        check();
        update();

        stateIndexType = STATE_TYPE.WAIT;
        sleep(1000);

        s.encoder(0, position(2), 0, 1.0);
        stateIndexType = STATE_TYPE.ENCODER;
        check();
        update();

        stateIndexType = STATE_TYPE.WAIT;
        sleep(1000);

        s.encoder(position(2), position(2), 1.0, 1.0);
        stateIndexType = STATE_TYPE.ENCODER;
        check();
        update();

        stateIndexType = STATE_TYPE.WAIT;
        sleep(1000);*/

        /*while (!s.stop) {
            switch (stateIndex) {
                case 1:
                    s.encoderStraight(position(2), 1.0);
                    stateIndexType = STATE_TYPE.ENCODER_STRAIGHT;
                    break;
                case 2:
                    s.encoderTurn(State.DIRECTION.LEFT, position(2), 1);
                    stateIndexType = STATE_TYPE.ENCODER_TURN;
                    break;
                case 2:
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
                case 3:
                    stateIndexType = STATE_TYPE.STOP;
                    s.stop = true;
                    break;
            }
            while (!s.isComplete(stateIndexType)) {
                addTelemetry("Waiting");
                waitOneFullHardwareCycle();
            }
            s.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            s.update();
            stateIndex+=1;
            addTelemetry("Done");
        }*/

//        s.kill();
    }

    protected void addTelemetry() {
        telemetry.addData("KEY", motorLeft.getCurrentPosition() + "-" + motorLeft.getTargetPosition() + "-" + motorRight.getCurrentPosition() + "-" + motorRight.getTargetPosition());
    }

    protected int position(double rotation) {
        return (int) Math.round(rotation*MOTOR_PPR);
    }

}
