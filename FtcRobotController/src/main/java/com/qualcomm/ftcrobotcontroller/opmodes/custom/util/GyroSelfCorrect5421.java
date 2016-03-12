package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;

/**
 * Created by Simon on 3/11/2016.
 */
public class GyroSelfCorrect5421 extends RMOpMode {

    GyroSensor gyro;
    Motor mL;
    Motor sL;
    Motor mR;
    Motor sR;

    @Override
    public void init() {
        super.setTeam(5421);
        super.init();
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        mL = motorMap.get("mL");
        mL.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        sL = motorMap.get("sL");
        mR = motorMap.get("mR");
        mR.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        sR = motorMap.get("sR");
    }

    public void init_loop() {
        telemetry.addData("CALIBRATION DONE:", !gyro.isCalibrating());
    }

    @Override
    protected void calculate() {
        if (gyro.getHeading() > 2) {
            drive(0.1, 0.3);
        } else if (gyro.getHeading() < 358) {
            drive(0.3, 0.1);
        } else {
            drive(0.15);
        }
        updateSlave();
        addTelemetry();
    }

    private void drive(double p) {
        mL.setDesiredPower(-p);
        mR.setDesiredPower(-p);
    }

    private void drive(double p1, double p2) {
        mL.setDesiredPower(-p1);
        mR.setDesiredPower(-p2);
    }

    private void updateSlave() {
        sL.setDesiredPower(mL.getDesiredPower());
        sR.setDesiredPower(mR.getDesiredPower());
    }

    private void addTelemetry() {
        telemetry.addData("h", String.format("%03d", gyro.getHeading()));
        telemetry.addData("mL", mL.getPower());
        telemetry.addData("sL", sL.getPower());
        telemetry.addData("mR", mR.getPower());
        telemetry.addData("sR", sR.getPower());
    }
}
