package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Dpad;
import com.rmrobotics.library.control.Joystick;
import com.rmrobotics.library.core.RMOpMode;

public class ServoCalibration extends RMOpMode {

    @Override
    public void init() {
        super.setTeam(5421);
        super.init();
    }

    @Override
    protected void calculate() {
        double leftFlap = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos = servoMap.get("bL").getPosition();
        double rFlapPos = servoMap.get("bR").getPosition();
        if (leftFlap > 0.2) {
            lFlapPos += 0.01;
            servoMap.get("bL").setDesiredPosition(lFlapPos);
        } else if (leftFlap < -0.2) {
            lFlapPos -= 0.01;
            servoMap.get("bL").setDesiredPosition(lFlapPos);
        }
        if (rightFlap > 0.2) {
            rFlapPos += 0.01;
            servoMap.get("bR").setDesiredPosition(rFlapPos);
        } else if (rightFlap < -0.2) {
            rFlapPos -= 0.01;
            servoMap.get("bR").setDesiredPosition(rFlapPos);
        }
        boolean upL = control.dpadValue(Controller.C_ONE, Dpad.DPAD_LEFT);
        boolean downL = control.button(Controller.C_ONE, Button.BUTTON_X);
        boolean upR = control.dpadValue(Controller.C_ONE, Dpad.DPAD_RIGHT);
        boolean downR = control.button(Controller.C_ONE, Button.BUTTON_B);
        double lHPos = servoMap.get("hL").getPosition();
        double rHPos = servoMap.get("hR").getPosition();
        if (upL) {
            lHPos += 0.01;
            servoMap.get("hL").setDesiredPosition(lHPos);
        } else if (downL) {
            lHPos -= 0.01;
            servoMap.get("hL").setDesiredPosition(lHPos);
        }
        if (upR) {
            rHPos += 0.01;
            servoMap.get("hR").setDesiredPosition(rHPos);
        } else if (downR) {
            rHPos -= 0.01;
            servoMap.get("hR").setDesiredPosition(rHPos);
        }
        telemetry.addData("L-LJ-R-RJ", lFlapPos + " " + leftFlap + " " + rFlapPos + " " + rightFlap);
        telemetry.addData("HL-HR", servoMap.get("hL").getPosition() + "-" + servoMap.get("hR").getPosition());
    }
}