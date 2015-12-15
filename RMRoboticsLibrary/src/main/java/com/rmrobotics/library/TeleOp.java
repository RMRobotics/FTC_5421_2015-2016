package com.rmrobotics.library;


import com.rmrobotics.library.RMOpMode;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;

public class TeleOp extends RMOpMode {

    private final String CONFIGURATION_PATH = "res/robot.json";

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void calculate() {
        double leftPower = control.joystickValue(Controller.C_ONE, Joystick.J_LEFT, Axis.A_Y);
        double rightPower = control.joystickValue(Controller.C_ONE, Joystick.J_RIGHT, Axis.A_Y);
        motorMap.get("DriveLeftOne").setDesiredPower(leftPower);
        motorMap.get("DriveLeftTwo").setDesiredPower(leftPower);
        motorMap.get("DriveRightOne").setDesiredPower(rightPower);
        motorMap.get("DriveRightTwo").setDesiredPower(rightPower);

        boolean harvestUp = control.button(Controller.C_ONE, Button.BUTTON_LB);
        boolean harvestDown = control.button(Controller.C_ONE, Button.BUTTON_RB);
        double harvestPower;
        if(harvestUp){
            harvestPower = 1.0;
        }else if(harvestDown){
            harvestPower = -1.0;
        }else{
            harvestPower = 0.0;
        }
        motorMap.get("Harvester").setDesiredPower(harvestPower);

        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        double bucketPower;
        if(bucketLeft){
            bucketPower = 1.0;
        }else if(bucketRight){
            bucketPower = -1.0;
        }else{
            bucketPower = 0.0;
        }
        motorMap.get("Bucket").setDesiredPower(bucketPower);

        double leftFlap = control.joystickValue(Controller.C_TWO, Joystick.J_LEFT, Axis.A_Y);
        double rightFlap = control.joystickValue(Controller.C_TWO, Joystick.J_RIGHT, Axis.A_Y);
        double lFlapPos;
        double rFlapPos;
        if(leftFlap > 0.1){
            lFlapPos = 0.75;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }else if(leftFlap < -0.1){
            lFlapPos = 0.50;
            servoMap.get("BucketLeft").setDesiredPosition(lFlapPos);
        }
        if(rightFlap > 0.1){
            rFlapPos = 0.75;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
        }else if(rightFlap < -0.1){
            rFlapPos = 0.50;
            servoMap.get("BucketRight").setDesiredPosition(rFlapPos);
        }

        boolean climberThrowUp = control.button(Controller.C_TWO, Button.BUTTON_A);
        boolean climberThrowDown = control.button(Controller.C_TWO, Button.BUTTON_B);
        double climberPos;
        if(climberThrowUp){
            climberPos = 0.75;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
        }else if(climberThrowDown){
            climberPos = 0.50;
            servoMap.get("Climbers").setDesiredPosition(climberPos);
        }
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }
}
