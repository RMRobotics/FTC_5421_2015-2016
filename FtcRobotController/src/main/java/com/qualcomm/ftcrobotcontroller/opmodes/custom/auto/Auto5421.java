package com.qualcomm.ftcrobotcontroller.opmodes.custom.auto;

import com.rmrobotics.library.core.RMAutoMode;

import java.text.DecimalFormat;

/**
 * Created by Simon on 2/17/2016.
 */
public class Auto5421 extends RMAutoMode {
    DecimalFormat df = new DecimalFormat("#.##");

    private final String CONFIGURATION_PATH = "res/5421robot_cleanUpImprovement.JSON";

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void calculate() {
        
    }

    @Override
    protected String setConfigurationPath() {
        return CONFIGURATION_PATH;
    }

    private void addTelemetry() {
        telemetry.addData("L1-L2-R1-R2-H-B-LF-RF-C", df.format(motorMap.get("DriveLeftOne").getPower()) + "-"
                + df.format(motorMap.get("DriveLeftTwo").getPower()) + "-" + df.format(motorMap.get("DriveRightOne").getPower()) + "-"
                + df.format(motorMap.get("DriveRightTwo").getPower()) + "-" + df.format(motorMap.get("Harvester").getPower()) + "-"
                + df.format(motorMap.get("Bucket").getPower()) + "-" + df.format(servoMap.get("BucketLeft").getPosition()) + "-"
                + df.format(servoMap.get("BucketRight")) + "-" + df.format(servoMap.get("Climbers").getPosition()));
    }
}
