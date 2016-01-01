package com.qualcomm.ftcrobotcontroller;

import java.util.Calendar;

/**
 * Created by Simon on 12/31/2015.
 */

public class AutoState extends RMOpMode {
    private String state = "begin"

    @Override
    public void init() {
        super.init();
    }

    public void calculate() {
        while (state != "end") {
            switch (state) {
                case "begin":
                    state = "center";
                case "center":
                    System.out.println("Moving forward");
                    motorMap.get("DriveLeftOne").setDesiredPower(1.0);
                    motorMap.get("DriveLeftTwo").setDesiredPower(1.0);
                    motorMap.get("DriveRightOne").setDesiredPower(1.0);
                    motorMap.get("DriveRightTwo").setDesiredPower(1.0);

                    state = STATE2;

                    if (someQuitCheck == true) state = QUIT;

                    break;
                case STATE2:
                    System.out.println("Doing Task 2");

   /* Check conditions and possibly modify state */

                    state = STATE1;

                    if (someQuitCheck == true) state = QUIT;
                    break;
                default:
                    break;
            } // end switch

        } // end while
    }
}