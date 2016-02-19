
package com.qualcomm.ftcrobotcontroller.opmodes.custom.util;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.rmrobotics.library.core.RMOpMode;

/**
 * Created by Peter on 2/3/16.
 */
public class OpticalSensorTest extends RMOpMode {
    OpticalDistanceSensor ost;
    @Override
    protected String setConfigurationPath()
    {
        return "{\n" + "}";
    }

    @Override
    protected void calculate()
    {
        telemetry.addData("LightDetected:",ost.getLightDetected());
        telemetry.addData("RawLight",ost.getLightDetectedRaw());
    }

    public void init()
    {
        hardwareMap.logDevices();
        ost = hardwareMap.opticalDistanceSensor.get("ost");
    }
}
