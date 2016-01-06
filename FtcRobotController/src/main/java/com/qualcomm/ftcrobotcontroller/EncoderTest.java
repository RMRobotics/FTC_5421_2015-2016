package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Timer;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 1/4/16.
 */
public class EncoderTest extends RMOpMode {
    Timer timer = new Timer();
    Calendar cal;
    private long curTime;
    private long startTime;

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"motor\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "  ],\n" +
            "}";

    public void init() {
        super.init();
    }

    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    protected void calculate() {
        motorMap.get("motor").setRotationDistance(2.0);
        motorMap.get("motor").setEncoderPosition(0.5);
        telemetry.addData("calc", "current position at " + motorMap.get("motor").getCurrentPosition() + " and target position at " + motorMap.get("motor").getTargetPosition());
    }
/*
    private void waitTime(long wait) {
        curTime = cal.getTimeInMillis();
        startTime = cal.getTimeInMillis();
        while ((curTime-startTime)<wait){
            telemetry.addData("wait","Waiting");
            DbgLog.msg("Waiting");
            curTime = cal.getTimeInMillis();
        }
    }
}
*/
}