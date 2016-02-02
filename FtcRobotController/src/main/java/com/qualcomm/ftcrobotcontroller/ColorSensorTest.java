package com.qualcomm.ftcrobotcontroller;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ColorSensorTest extends RMOpMode{

    public enum ColorSensorDevice {MODERN_ROBOTICS_I2C}

    public ColorSensorDevice device = ColorSensorDevice.MODERN_ROBOTICS_I2C;

    ColorSensor colorSensor;
    DeviceInterfaceModule cdim;
    LED led;
    TouchSensor t;

    private final String CONFIGURATION_PATH = "{\n" +
            "}";

    @Override
    public void init() {
        hardwareMap.logDevices();

        cdim = hardwareMap.deviceInterfaceModule.get("dim");

        colorSensor = hardwareMap.colorSensor.get("mr");

        led = hardwareMap.led.get("led");
        t = hardwareMap.touchSensor.get("t");

    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    public void calculate() {

        float hsvValues[] = {0, 0, 0};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        enableLed(t.isPressed());

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);


        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
    }

    private void enableLed(boolean value) {

                colorSensor.enableLed(value);
        }
    }