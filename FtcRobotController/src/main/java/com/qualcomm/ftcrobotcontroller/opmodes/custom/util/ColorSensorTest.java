package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.rmrobotics.library.core.RMOpMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ColorSensorTest extends RMOpMode{
    Calendar cal;
    List l;
    ColorSensor colorSensor;
    private final String CONFIGURATION_PATH = "  \"motors\":[\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "  ],\n" +
            "}";

    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("cS");
        colorSensor.enableLed(false);
        l = new ArrayList();
        cal = new GregorianCalendar();
        super.init();
        telemetry.addData("Init","Stuff");
    }

    @Override
    protected String setConfigurationPath() {
        telemetry.addData("Config","Stuff");
        return CONFIGURATION_PATH;
    }

    public void calculate() {
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        determine();
    }
    public void determine() {
        Integer myColorNumber = 1;
        switch (myColorNumber) {
            case 1:
                if (colorSensor.blue() > colorSensor.red()+10) {
                    telemetry.addData("Good ", "Data");
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Go until reaches desired distance
                }
                else if (colorSensor.red() > colorSensor.blue()+10) {
                    telemetry.addData("Bad ", "Data");

                    motorMap.get("LeftMotor").setDesiredPower(-0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Loops this until turned desire angle
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Loops this until moved desired distance
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(-0.3);
                    //Loops this until turned desired angle
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Move until reached desired distance
                }
                else {
                    telemetry.addData("Null ", "Data");
                }
                break;
            case 2:
                if (colorSensor.red() > colorSensor.blue()+10) {
                    telemetry.addData("Good ", "Data");
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //go until reached desired distance
                }
                else if (colorSensor.blue() > colorSensor.red()+10) {
                    telemetry.addData("Bad ", "Data");
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(-0.3);
                    //Loops this until turned desire angle
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Loops this until moved desired distance
                    motorMap.get("LeftMotor").setDesiredPower(-0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Loops this until turned desired angle
                    motorMap.get("LeftMotor").setDesiredPower(0.3);
                    motorMap.get("RightMotor").setDesiredPower(0.3);
                    //Move until reached desired distance
                }
                else {
                    telemetry.addData("Null ", "Data");
                }
                break;


        }}}
