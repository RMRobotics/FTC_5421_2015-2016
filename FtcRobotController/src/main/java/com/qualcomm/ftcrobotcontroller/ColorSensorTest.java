package com.qualcomm.ftcrobotcontroller;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensorTest extends RMOpMode{

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
                }
                else if (colorSensor.red() > colorSensor.blue()+10) {
                    telemetry.addData("Bad ", "Data");
                }
                else {
                    telemetry.addData("Null ", "Data");
                }
                break;
            case 2:
                if (colorSensor.red() > colorSensor.blue()+10) {
                    telemetry.addData("Good ", "Data");
                }
                else if (colorSensor.blue() > colorSensor.red()+10) {
                    telemetry.addData("Bad ", "Data");
                }
                else {
                    telemetry.addData("Null ", "Data");
                }
                break;


    }}}
