package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;

import java.util.Calendar;

/**
 * Created by Peter on 1/4/16.
 */
public class EncoderTest extends RMOpMode {
    Calendar cal;
    protected long curTime;
    protected long startTime;

    private final String CONFIGURATION_PATH = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"motor\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "      \"mode\":\"RUN_TO_POSITION\"\n" +
            "    },\n" +
            "  ],\n" +
            "}";

    public void init() {
        super.init();
    }

    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    public void loop() {
        while (1==1){
            motorMap.get("motor").setRotationDistance(1.0);
            waitTime(5);
            motorMap.get("motor").setRotationDistance(-0.5);
            waitTime(5);
        }
    }

    public void calculate(){}

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
