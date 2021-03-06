/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.ftcrobotcontroller.opmodes.custom.auto.BeaconParkAuto5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.auto.MountainBlueAuto5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.auto.MountainRedAuto5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.teleop.TeleOp5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.util.ColorSensorTest5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.util.DemoBot;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.util.GyroSelfCorrect5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.util.GyroTest5421;
import com.qualcomm.ftcrobotcontroller.opmodes.custom.util.ServoCalibration;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

/**
 * Register Op Modes
 */
public class FtcOpModeRegister implements OpModeRegister {

    /**
     * The Op Mode Manager will call this method when it wants a list of all
     * available op modes. Add your op mode to the list to enable it.
     *
     * @param manager op mode manager
     */
    public void register(OpModeManager manager) {

      /*
       * register your op modes here.
       * The first parameter is the name of the op mode
       * The second parameter is the op mode class property
       *
       * If two or more op modes are registered with the same name, the app will display an error.
       */

      /*
       * Uncomment any of the following lines if you want to register an op mode.
       */

        //  manager.register("ServoTest", ServoTest.class);
        manager.register("ServoCalibration", ServoCalibration.class);
        //  manager.register("ColorSensorTest", ColorSensorTest.class);
        // manager.register("EncoderTest", EncoderTest.class);
        //  manager.register("OpticalSensorTest", OpticalSensorTest.class);

        // manager.register("TeleOp8121", TeleOp8121.class);
        // manager.register("Auto8121", Auto8121.class);
        // manager.register("StateAuto8121", StateAuto8121.class);

        manager.register("TeleOp5421", TeleOp5421.class);

        manager.register("MountainRedAuto5421", MountainRedAuto5421.class);
        manager.register("MountainBlueAuto5421", MountainBlueAuto5421.class);
        manager.register("BeaconParkAuto5421", BeaconParkAuto5421.class);

        manager.register("GyroTest5421", GyroTest5421.class);
        manager.register("GryoSelfCorrection", GyroSelfCorrect5421.class);
        manager.register("ColorSensorTest5421", ColorSensorTest5421.class);
        //manager.register("Testify5421", TestifyOp5421.class);
        manager.register("DemoBot5421", DemoBot.class);

        //manager.register("Gyro", MRGyroTest.class);

        //manager.register("navXCollisionDetectionOp", navXCollisionDetectionOp.class);
        //manager.register("navXDriveStraightPIDLinearOp", navXDriveStraightPIDLinearOp.class);
        //manager.register("navXDriveStraightPIDLoopOp", navXDriveStraightPIDLoopOp.class);
        //manager.register("navXMotionDetectionOp", navXMotionDetectionOp.class);
        //manager.register("navXPerformanceTuningOp", navXPerformanceTuningOp.class);
        //manager.register("navXProcessedOp", navXProcessedOp.class);
        //manager.register("navXRawOp", navXRawOp.class);
        //manager.register("navXRotateToAnglePIDLinearOp", navXRotateToAnglePIDLinearOp.class);
        //manager.register("navXRotateToAnglePIDLoopOp", navXRotateToAnglePIDLoopOp.class);
        //manager.register("navXZeroYawOp", navXZeroYawOp.class);

    }
}