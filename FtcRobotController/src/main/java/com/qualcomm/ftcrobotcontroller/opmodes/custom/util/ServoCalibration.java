package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Controller;
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
        double leftFlap = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
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
        telemetry.addData("L-LJ-R-RJ", lFlapPos + " " + leftFlap + " " + rFlapPos + " " + rightFlap);
    }
}