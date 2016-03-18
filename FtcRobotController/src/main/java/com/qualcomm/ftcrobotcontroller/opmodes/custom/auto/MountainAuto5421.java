package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.rmrobotics.library.core.RMAutoMode;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.util.StateType;

import java.text.DecimalFormat;

public class MountainAuto5421 extends RMAutoMode {

    private final int WAIT = 5421;

    int prevState;
    int state = 1;
    StateType prevStateType;
    double sleepTime;
    double turnTarget;
    String skew;
    double mLdP;
    double mRdP;

    GyroSensor gyro;

    Motor mL;
    Motor sL;
    Motor mR;
    Motor sR;
    Motor wL;
    Motor wR;
    Motor h;
    //rServo climbers;
    ElapsedTime runTime;

    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat nf = new DecimalFormat("########");

    @Override
    public void init() {
        super.setTeam(5421);
        super.init();
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        mL = motorMap.get("mL");
        sL = motorMap.get("sL");
        mR = motorMap.get("mR");
        sR = motorMap.get("sR");
        wL = motorMap.get("wL");
        wR = motorMap.get("wR");
        h = motorMap.get("h");
        //climbers = servoMap.get("climbers");
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        addTelemetry();
        runTime.reset();
    }

    @Override
    protected void calculate() {
        for (Motor m : motorMap.values()) {
            if (m.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) {
                m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            }
        }
        switch (state) {
            case 1:
                turnTarget = 0;
                setDriveTarget(10000);
                setDrivePower(0.5);
                updateDP();
                updateState(StateType.GYRO_ENCODER_DRIVE);
                break;
            case 2:
                turnTarget = 270;
                setDrivePower(0, 0.5);
                updateDP();
                updateState(StateType.GYRO);
                break;
            case 3:
                setDriveTarget(10000);
                setDrivePower(0.5);
                updateDP();
                updateState(StateType.GYRO_ENCODER_DRIVE);
                break;
            default:
                stop();
                break;
            case WAIT:
                switch (prevStateType) {
                    case ENCODER_DRIVE:
                        if (driveDone()) {
                            updateStateWait();
                            driveStop();
                        }
                        break;
                    case GYRO_ENCODER_DRIVE:
                        if (gyroEncoderDriveDone()) {
                            updateStateWait();
                            driveStop();
                        }
                        break;
                    case GYRO_DRIVE:
                        if (gyroTimeDriveDone()) {
                            updateStateWait();
                            driveStop();
                        }
                        break;
                    case SLEEP:
                        if (timeDone()) {
                            updateStateWait();
                        }
                        break;
                    case GYRO:
                        if (gyroDone()) {
                            updateStateWait();
                        }
                        break;
                }
        }
        updateSlave();
        addTelemetry();
    }

    private void addTelemetry() {
        telemetry.addData("TIME", runTime.time());
        telemetry.addData("GYRO", gyro.getHeading() + "-" + skew + "-" + !gyro.isCalibrating());
        telemetry.addData("WR", df.format(wR.getPower()));
        telemetry.addData("WL", df.format(wL.getPower()));
        telemetry.addData("H", df.format(h.getPower()));
        telemetry.addData("SR", df.format(sR.getPower()));
        telemetry.addData("SL", df.format(sL.getPower()));
        telemetry.addData("MR-RT-RP", df.format(mR.getPower()) + "-" + nf.format(mR.getTargetPosition()) + "-" + nf.format(mR.getCurrentPosition()));
        telemetry.addData("ML-LT-LP", df.format(mL.getPower()) + "-" + nf.format(mL.getTargetPosition()) + "-" + nf.format(mL.getCurrentPosition()));
        telemetry.addData("S-P", state);
    }

    private void setDriveTarget(int target) {
        mL.setTargetPosition(target);
        mR.setTargetPosition(target);
    }

    private void setDriveTarget(int targetLeft, int targetRight) {
        mL.setTargetPosition(targetLeft);
        mR.setTargetPosition(targetRight);
    }

    private void setDrivePower(double power) {
        mL.setDesiredPower(power);
        mR.setDesiredPower(power);
    }

    private void setDrivePower(double powerLeft, double powerRight) {
        mL.setDesiredPower(powerLeft);
        mR.setDesiredPower(powerRight);
    }

    private void updateState(StateType type) {
        runTime.reset();
        prevState = state;
        prevStateType = type;
        state = WAIT;
    }

    private void updateStateWait() {
        state = prevState + 1;
        prevState = WAIT;
        prevStateType = StateType.WAIT;
    }

    private void updateSlave() {
        sL.setDesiredPower(mL.getDesiredPower());
        sR.setDesiredPower(mR.getDesiredPower());
    }

    private void updateDP() {
        mLdP = mL.getDesiredPower();
        mRdP = mR.getDesiredPower();
    }

    private boolean driveDone() {
        if (Math.abs(mL.getCurrentPosition()) > mL.getTargetPosition()) {
            mL.setDesiredPower(0);
        }
        if (Math.abs(mR.getCurrentPosition()) > mR.getTargetPosition()) {
            mR.setDesiredPower(0);
        }
        if (mL.getPower() < 0.01 && mR.getPower() < 0.01) {
            return true;
        } else {
            return false;
        }
    }

    private boolean gyroDone() {
        if (Math.abs(gyro.getHeading() - turnTarget) < 3) {
            return true;
        } else {
            return false;
        }
    }

    private boolean gyroEncoderDriveDone() {
        if (Math.abs(mL.getCurrentPosition()) < mL.getTargetPosition() && Math.abs(mR.getCurrentPosition()) < mR.getTargetPosition()) {
            gyroDrive();
            return false;
        } else {
            return true;
        }
    }

    private boolean gyroTimeDriveDone() {
        if (runTime.time() <= sleepTime) {
            gyroDrive();
            return false;
        } else {
            return true;
        }
    }

    private void gyroDrive() {
        if (turnTarget > 1 && turnTarget < 180) {
            if (gyro.getHeading() > turnTarget + 1 && gyro.getHeading() < turnTarget + 180) {
                if (Math.abs(gyro.getHeading() - turnTarget) > 5) {
                    setDrivePower(0, mRdP);
                } else {
                    setDrivePower(mLdP - 0.1, mRdP);
                }
                skew = "RIGHT";
            } else if (gyro.getHeading() < turnTarget - 1 || gyro.getHeading() > turnTarget + 180) {
                if ((gyro.getHeading() > turnTarget && Math.abs(gyro.getHeading() - turnTarget) > 5) || (gyro.getHeading() < turnTarget && Math.abs(gyro.getHeading() - turnTarget) > 5)) {
                    setDrivePower(mLdP, 0);
                } else {
                    setDrivePower(mLdP, mRdP - 0.1);
                }
                skew = "LEFT";
            } else {
                setDrivePower(0.5);
                skew = "STRAIGHT";
            }
        } else if (turnTarget < 359 && turnTarget >= 180) {
            if (gyro.getHeading() < turnTarget - 1 && gyro.getHeading() > turnTarget - 180) {
                if (Math.abs(gyro.getHeading() - turnTarget) > 5) {
                    setDrivePower(mLdP, 0);
                } else {
                    setDrivePower(mLdP, mRdP - 0.1);
                }
                skew = "LEFT";
            } else if (gyro.getHeading() > turnTarget + 1 || gyro.getHeading() < turnTarget - 180) {
                if ((gyro.getHeading() > turnTarget && Math.abs(gyro.getHeading() - turnTarget) > 5) || (gyro.getHeading() < turnTarget - 180 && Math.abs(gyro.getHeading() - turnTarget) > 5)) {
                    setDrivePower(0, mRdP);
                } else {
                    setDrivePower(mLdP - 0.1, mRdP);
                }
                skew = "RIGHT";
            } else {
                setDrivePower(0.5);
                skew = "STRAIGHT";
            }
        } else if (turnTarget == 0) {
            if (gyro.getHeading() > 1 && gyro.getHeading() < 180) {
                if (gyro.getHeading() > 5) {
                    setDrivePower(0, mRdP);
                } else {
                    setDrivePower(mLdP - 0.1, mRdP);
                }
                skew = "RIGHT";
            } else if (gyro.getHeading() < 359 && gyro.getHeading() > 180) {
                if (gyro.getHeading() < 355) {
                    setDrivePower(mLdP, 0);
                } else {
                    setDrivePower(mLdP, mRdP - 0.1);
                }
                skew = "LEFT";
            } else {
                setDrivePower(0.5);
                skew = "STRAIGHT";
            }
        } else if (turnTarget == 1) {
            if (gyro.getHeading() > 2 && gyro.getHeading() < 180) {
                if (gyro.getHeading() > 6) {
                    setDrivePower(0, mRdP);
                } else {
                    setDrivePower(mLdP - 0.1, mRdP);
                }
                skew = "RIGHT";
            } else if (gyro.getHeading() < 360 && gyro.getHeading() > 180) {
                if (gyro.getHeading() < 356) {
                    setDrivePower(mLdP, 0);
                } else {
                    setDrivePower(mLdP, mRdP - 0.1);
                }
                skew = "LEFT";
            } else {
                setDrivePower(0.5);
                skew = "STRAIGHT";
            }
        } else if (turnTarget == 359) {
            if (gyro.getHeading() > 0 && gyro.getHeading() < 180) {
                if (gyro.getHeading() > 4) {
                    setDrivePower(0, mRdP);
                } else {
                    setDrivePower(mLdP - 0.1, mRdP);
                }
                skew = "RIGHT";
            } else if (gyro.getHeading() < 358 && gyro.getHeading() > 180) {
                if (gyro.getHeading() < 354) {
                    setDrivePower(mLdP, 0);
                } else {
                    setDrivePower(mLdP, mRdP - 0.1);
                }
                skew = "LEFT";
            } else {
                setDrivePower(0.5);
                skew = "STRAIGHT";
            }
        } else {
            setDrivePower(0.5);
            skew = "STRAIGHT";
        }
    }

    private boolean timeDone() {
        if (runTime.time() >= sleepTime) {
            return true;
        } else {
            return false;
        }
    }

    private void driveStop() {
        mL.setDesiredPower(0);
        mR.setDesiredPower(0);
    }

}