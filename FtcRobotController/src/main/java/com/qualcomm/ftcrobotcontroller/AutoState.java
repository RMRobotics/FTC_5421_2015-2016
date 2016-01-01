package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;

import java.util.Calendar;

/**
 * Created by Simon on 12/31/2015.
 */

public class AutoState extends RMOpMode {
    private String state = "begin";
    Calendar cal;
    protected long curTime;
    protected long startTime;

    @Override
    public void init() {
        super.init();
        cal = Calendar.getInstance();
    }

    public void calculate() {
        while (state != "end") {
            switch (state) {
                case "begin":
                    state = "center";
                case "center":
                    motorMap.get("DriveLeftOne").setDesiredPower(1.0);
                    motorMap.get("DriveLeftTwo").setDesiredPower(1.0);
                    motorMap.get("DriveRightOne").setDesiredPower(1.0);
                    motorMap.get("DriveRightTwo").setDesiredPower(1.0);
                    waitStatic(1000);
                    state = "turnLeft45";
                case "turnLeft45":
                    motorMap.get("DriveLeftOne").setDesiredPower(0.0);
                    motorMap.get("DriveLeftTwo").setDesiredPower(0.0);
                    motorMap.get("DriveRightOne").setDesiredPower(1.0);
                    motorMap.get("DriveRightTwo").setDesiredPower(1.0);
                    waitStatic(1000);
                    state = "beaconZone";
                case "beaconZone":
                    


   /* Check conditions and possibly modify state */

                    state = STATE1;

                    if (someQuitCheck == true) state = QUIT;
                    break;
                default:
                    break;
            } // end switch

        } // end while
    }

    private void waitStatic(long wait) {
        curTime = cal.getTimeInMillis();
        startTime = cal.getTimeInMillis();
        while ((curTime-startTime)<wait){
            telemetry.addData("wait","Waiting");
            DbgLog.msg("Waiting");
            curTime = cal.getTimeInMillis();
        }
    }
}