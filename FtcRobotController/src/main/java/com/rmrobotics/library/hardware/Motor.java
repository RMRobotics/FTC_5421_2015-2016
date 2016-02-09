package com.rmrobotics.library.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import static java.lang.Math.abs;

public class Motor {

    private static final double MAX_POWER = 1.0;
    private static final double MIN_POWER = 0.1;
    private static final double MAX_ACCEL = 0.03;
    private static final double NVRST20_ENC = 560;
    private static final double NVRST40_ENC = 1120;
    private static final double NVRST60_ENC = 1680;

    private DcMotor parent;
    private DcMotor.Direction defaultDirection;
    private double minPower;
    private double maxPower;
    private MOTOR_TYPE motorType;
    private double desiredPower;
    private double currentPower;
    private int curPos;
    private int tarPos;
    private int dis;

    public Motor(DcMotor dc, DcMotor.Direction d, double min, double max, MOTOR_TYPE mType) {
        parent = dc;
        defaultDirection = d;
        minPower = min;
        maxPower = max;
        motorType = mType;

    } //Todo add string to send in DbgLog confirming motor settings once configured

    public void setDesiredPower(double d) {
        desiredPower = d;
    }

    public void updateCurrentPower() {
        /**
         * let it be zero if it needs to be
         * absolute value and switch direction if needed
         * clip to be within range
         *
         */
        double absDesPower = abs(desiredPower);
        if (parent.getTargetPosition() == parent.getCurrentPosition() && parent.getMode() == DcMotorController.RunMode.RUN_TO_POSITION) {
            desiredPower = 0;
            currentPower = desiredPower;
        } else if (absDesPower == 0.0 || absDesPower < minPower) {
            currentPower = desiredPower;
        } else {
            if (desiredPower < 0.0) {
                if (defaultDirection == DcMotor.Direction.FORWARD) {
                    parent.setDirection(DcMotor.Direction.REVERSE);
                } else if (defaultDirection == DcMotor.Direction.REVERSE) {
                    parent.setDirection(DcMotor.Direction.FORWARD);
                }
            } else {
                if (defaultDirection == DcMotor.Direction.FORWARD) {
                    parent.setDirection(DcMotor.Direction.FORWARD);
                } else if (defaultDirection == DcMotor.Direction.REVERSE) {
                    parent.setDirection(DcMotor.Direction.REVERSE);
                }
            }
            desiredPower = absDesPower;
            if (desiredPower > maxPower) {
                desiredPower = maxPower;
            }
            currentPower = desiredPower;
        }
    }

    public void setCurrentPower() {
        if (desiredPower > currentPower) {
            if (currentPower < 0.2 && Math.abs(currentPower - desiredPower) > 0.2) {
                currentPower += MAX_ACCEL;
            } else {
                currentPower += 0.05*(Math.abs(currentPower - desiredPower));
            }
            parent.setPower(currentPower);
        } else if (desiredPower < currentPower) {
            if (currentPower > 0.08 && Math.abs(currentPower - desiredPower) > 0.2) {
                currentPower -= MAX_ACCEL;
            } else {
                currentPower -= 0.05*(Math.abs(currentPower - desiredPower));
            }
            parent.setPower(currentPower);
        } else {
            parent.setPower(currentPower);
        }
    }

    public void setEncoderMove(double rotation, double power) {
        curPos = parent.getCurrentPosition();
        tarPos = curPos + (int)(rotation * 1120);
        parent.setTargetPosition(tarPos);
        setDesiredPower(power);
    }

    public double getCurrentPosition() {
        return parent.getCurrentPosition();
    }

    public double getTargetPosition() {
        return parent.getTargetPosition();
    }

    public DcMotorController.RunMode getMode() {
        return parent.getMode();
    }

    public void resetEncoder() {
        parent.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void runToPosition() {
        parent.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    public void runWithoutEncoders() { parent.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS); }

    public double getPower() { return parent.getPower(); }

    public double getDesiredPower() { return desiredPower; }

    public String getMotorType() {
        return motorType.toString();
    }

    public void stop() { setDesiredPower(0); }

}