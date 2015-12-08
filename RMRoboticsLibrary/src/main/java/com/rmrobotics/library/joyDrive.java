package com.rmrobotics.library;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.rmrobotics.library.Motor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.rmrobotics.library.control.Axis;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.control.Joystick;


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
