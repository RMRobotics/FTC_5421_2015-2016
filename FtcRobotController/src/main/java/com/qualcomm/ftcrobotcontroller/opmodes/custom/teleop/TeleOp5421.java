package com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop;

import android.graphics.YuvImage;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.control.Trigger;
import com.rmrobotics.library.core.RMOpMode;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;

import java.text.DecimalFormat;

public class TeleOp5421 extends RMOpMode {
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat nf = new DecimalFormat("########");

    Motor mL;
    Motor sL;
    Motor mR;
    Motor sR;
    Motor wL;
    Motor wR;
    Motor h;
    DcMotor led;
    rServo bL;
    rServo bR;
    rServo b;
    rServo hL;
    rServo hR;
    GyroSensor gyro;
    ElapsedTime runTime;

    @Override
    public void init() {
        super.setTeam(5421);
        super.init();
        mL = motorMap.get("mL");
        sL = motorMap.get("sL");
        mR = motorMap.get("mR");
        sR = motorMap.get("sR");
        wL = motorMap.get("wL");
        wR = motorMap.get("wR");
        h = motorMap.get("h");
        led = hardwareMap.dcMotor.get("led");
        bL = servoMap.get("bL");
        bR = servoMap.get("bR");
        b = servoMap.get("b");
        b.setDesiredPosition(0.5);
        hL = servoMap.get("hL");
        hR = servoMap.get("hR");
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        runTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        led.setPower(0.5);
        addTelemetry();
        runTime.reset();
    }

    public void init_loop() {
        addTelemetry();
    }

    @Override
    protected void calculate() {

        for (Motor m : motorMap.values()) {
            if (m.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
                m.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            }
        }

        addTelemetry();

        //drive
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        mL.setDesiredPower(leftPower);
        sL.setDesiredPower(leftPower);
        mR.setDesiredPower(rightPower);
        sR.setDesiredPower(rightPower);

        //harvester
        boolean harvestUp = control.button(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvestDown = control.button(Controller.C_ONE, Button.BUTTON_RB);
        double harvestPower;
        if(harvestUp){
            harvestPower = -1.0;
        }else if(harvestDown){
            harvestPower = 1.0;
        }else{
            harvestPower = 0.0;
        }
        h.setDesiredPower(harvestPower);

        //winch
        double windLeft = control.triggerValue(Controller.C_TWO, Trigger.T_LEFT);
        double windRight = control.triggerValue(Controller.C_TWO, Trigger.T_RIGHT);
        boolean unwindLeft = control.dpadValue(Controller.C_TWO, Dpad.DPAD_LEFT);
        boolean unwindRight = control.dpadValue(Controller.C_TWO, Dpad.DPAD_RIGHT);
        boolean windDown = control.dpadValue(Controller.C_TWO, Dpad.DPAD_DOWN);
        boolean Brake = control.dpadValue(Controller.C_TWO, Dpad.DPAD_UP);
        if (windDown) {
            wL.setDesiredPower(-1.0);
            wR.setDesiredPower(-1.0);
        } else {
            if (Brake) {
                wL.setDesiredPower(0.0);
                wR.setDesiredPower(0.0);
            }
            if (windLeft > 0.7) {
                wL.setDesiredPower(1.0);
            } else if (unwindLeft) {
                wL.setDesiredPower(-1.0);
            } else {
                wL.setDesiredPower(0);
            }
            if (windRight > 0.7) {
                wR.setDesiredPower(1.0);
            } else if (unwindRight) {
                wR.setDesiredPower(-1.0);
            } else {
                wR.setDesiredPower(0);
            }
        }

        //hook
        boolean hUp = control.button(Controller.C_TWO, Button.BUTTON_Y);
        boolean hDown = control.button(Controller.C_TWO, Button.BUTTON_A);
        boolean hLeftUp = control.button(Controller.C_TWO, Button.BUTTON_X);
        boolean hRightUp = control.button(Controller.C_TWO, Button.BUTTON_B);
        if (hUp) {
            hL.setDesiredPosition(1.0);
            hR.setDesiredPosition(1.0);
        } else if (hDown) {
            hL.setDesiredPosition(0);
            hR.setDesiredPosition(0);
        } else if (hLeftUp) {
            hL.setDesiredPosition(1.0);
        } else if (hRightUp) {
            hR.setDesiredPosition(1.0);
        }

        //bucket
        boolean bLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bSpeed;
        if(bRight){
            bSpeed = 1.0;
        }else if(bLeft) {
            bSpeed = 0;
        }else{
            bSpeed = 0.5;
        }
        b.setDesiredPosition(bSpeed);

        //flaps
        double flapLeft = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double flapRight = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos;
        double rFlapPos;
        if(flapLeft > 0.2){
            lFlapPos = 1;
            bL.setDesiredPosition(lFlapPos);
        }else if(flapLeft < -0.2){
            lFlapPos = 0;
            bL.setDesiredPosition(lFlapPos);
        }
        if(flapRight > 0.2){
            rFlapPos = 0;
            bR.setDesiredPosition(rFlapPos);
        }else if(flapRight < -0.2){
            rFlapPos = 1;
            bR.setDesiredPosition(rFlapPos);
        }

        //led
        if (runTime.time() > 30000) {
            led.setPower(-0.5);
        } else {
            led.setPower(0.5);
        }
        addTelemetry();
    }

    private String ledStatus(DcMotor.Direction d) {
        if (d == DcMotor.Direction.FORWARD) {
            return "ALLIANCE";
        } else if (d == DcMotor.Direction.REVERSE) {
            return "INDICATOR";
        } else {
            return "OFF";
        }
    }

    private void addTelemetry() {
        telemetry.addData("TIME", runTime.time());
        telemetry.addData("GYRO", gyro.getHeading() + "-" + !gyro.isCalibrating());
        telemetry.addData("LED", ledStatus(led.getDirection()));
        telemetry.addData("WR", df.format(wR.getPower()));
        telemetry.addData("WL", df.format(wL.getPower()));
        telemetry.addData("H", df.format(h.getPower()));
        telemetry.addData("SR", df.format(sR.getPower()));
        telemetry.addData("SL", df.format(sL.getPower()));
        telemetry.addData("MR-RT-RP", df.format(mR.getPower()) + "-" + nf.format(mR.getTargetPosition()) + "-" + nf.format(mR.getCurrentPosition()));
        telemetry.addData("ML-LT-LP", df.format(mL.getPower()) + "-" + nf.format(mL.getTargetPosition()) + "-" + nf.format(mL.getCurrentPosition()));
    }
}
