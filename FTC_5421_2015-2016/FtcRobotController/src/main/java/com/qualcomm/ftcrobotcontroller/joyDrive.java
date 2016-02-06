package com.qualcomm.ftcrobotcontroller;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.ftcrobotcontroller.Motor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.ftcrobotcontroller.control.Axis;
import com.qualcomm.ftcrobotcontroller.control.Control;
import com.qualcomm.ftcrobotcontroller.control.Controller;
import com.qualcomm.ftcrobotcontroller.control.Joystick;


/**
 * Created by JaJaJaJaJa on 12/7/15.
 */
public class joyDrive {
    private double joyY;
    private double power;

    public joyDrive(double oneVertical, double desiredPower){
        joyY = oneVertical;
        power = desiredPower;
    }

    public void Exponential(){

    }
}
