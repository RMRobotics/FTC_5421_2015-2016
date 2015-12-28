package com.rmrobotics.library;

import com.qualcomm.ftccommon.DbgLog; //for DbgLog
import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.Motor;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Joystick;
/**
 * Created by RM Robotics on 12/23/2015.
 */
public class R2D2 extends RMOpMode {
    private final String CONFIGURATION_PATH = "res/8121.json";
    public R2D2() {
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void calculate() {
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("DriveLeftOne").setDesiredPower(leftPower);
        motorMap.get("DriveLeftTwo").setDesiredPower(leftPower);
        motorMap.get("DriveRightOne").setDesiredPower(rightPower);
        motorMap.get("DriveRightTwo").setDesiredPower(rightPower);

        boolean armPress = control.buttonHeld(Controller.C_TWO, Button.BUTTON_LB);
        boolean armRetract = control.buttonHeld(Controller.C_TWO, Button.BUTTON_RB);
        double armPower;
        if(armPress){
            armPower = .5;
        }else if(armRetract){
            armPower = -.5;
        }else{
            armPower = 0;
        }
        motorMap.get("Arm").setDesiredPower(armPower);

    }
    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

}
