package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by RM Robotics on 10/24/2015.
 */
    public class K21TeleOp extends OpMode {

        DcMotor motorRight;
        DcMotor motorLeft;
        DcMotor motorCenter;
        float counter1;
        float counter2;



        @Override
        public void init() {

            motorRight = hardwareMap.dcMotor.get("motor_1");
            motorLeft = hardwareMap.dcMotor.get("motor_2");
            motorCenter = hardwareMap.dcMotor.get("motor_3");
            motorLeft.setDirection(DcMotor.Direction.REVERSE);

        }
        public void loop () {

            float leftPower = gamepad1.left_stick_y;
            float rightPower = gamepad1.right_stick_y;
            float right = rightPower;
            float left = leftPower;
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);
            right = (float)scaleInput(right);
            left =  (float)scaleInput(left);
            motorLeft.setPower(left);
            motorRight.setPower(right);

            if (gamepad1.a) {
                counter1 += 1;
                motorCenter.setPower(-.6);
                counter2 += 2;
            }
            else if (gamepad1.b) {
                motorCenter.setPower(.6);
            }
            else {
                motorCenter.setPower(0);
            }

            telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
            telemetry.addData("# A pressed", "A pressed: " + String.format("%.2f", counter1));
            telemetry.addData("# A response", "A responded: " + String.format("%.2f", counter2));
        }

        public void stop() {

        }

        double scaleInput(double dVal)  {
            double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                    0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

            // get the corresponding index for the scaleInput array.
            int index = (int) (dVal * 16.0);

            // index should be positive.
            if (index < 0) {
                index = -index;
            }

            // index cannot exceed size of array minus 1.
            if (index > 16) {
                index = 16;
            }

            // get value from the array.
            double dScale = 0.0;
            if (dVal < 0) {
                dScale = -scaleArray[index];
            } else {
                dScale = scaleArray[index];
            }

            // return scaled value.
            return dScale;
        }


    }
