
package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;

import com.rmrobotics.library.control.Button;
import com.rmrobotics.library.control.Controller;
import com.rmrobotics.library.core.RMOpMode;

import java.util.Calendar;
import java.util.Timer;

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
        /*
        boolean bucketLeft = control.button(Controller.C_TWO, Button.BUTTON_LB);
        boolean bucketRight = control.button(Controller.C_TWO, Button.BUTTON_RB);
        if(bucketLeft){
            motorMap.get("motor").setEncoderMove(0, 4.33333333, 1.0);
        }else if(bucketRight) {
            motorMap.get("motor").setEncoderMove(4853,-4.33333333, 1.0);
        }
        telemetry.addData("calc", "current position at " + motorMap.get("motor").getCurrentPosition() + " and target position at " + motorMap.get("motor").getTargetPosition() + " and power at " + motorMap.get("motor").getPower());
    }*/
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
}