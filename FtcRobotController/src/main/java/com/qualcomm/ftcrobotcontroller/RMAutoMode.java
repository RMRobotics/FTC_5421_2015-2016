package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.control.Control;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public abstract class RMAutoMode extends RMOpMode {

    protected List<State> stateList =  new ArrayList<State>();
    protected State currentState;

    protected abstract void setStateList();

    @Override
    public void init() {
        telemetry.addData("init", "init start");
        try {
            this.configureHardware(this.setConfigurationPath());
        } catch (IOException e) {
            e.printStackTrace();
            DbgLog.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            DbgLog.error(e.getMessage());
        }
        this.control = new Control(gamepad1, gamepad2);
        telemetry.addData("init","done");
    }

    @Override
    protected void configureHardware(String pathName) throws IOException, ParseException {
        telemetry.addData("configureHardware",pathName);
        telemetry.addData("configureHardware","configureHardware start");
        String configSource = readFile(pathName, Charset.defaultCharset());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(configSource);
        JSONArray jsonMotors = (JSONArray) jsonFile.get("motors");
        JSONArray jsonServos = (JSONArray) jsonFile.get("servos");
        this.configureMotorsAuton(jsonMotors);
        this.configureServos(jsonServos);
        //Todo add methods for configuring and sensors
        telemetry.addData("configureHardware","configureHardware finish");
    }

    @Override
    private static String readFile(String path, Charset encoding) throws IOException {
        /*byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
        */
        return path;
    }

    private void configureMotorsAuton(JSONArray JSONMotors) {
        telemetry.addData("configureMotors","configureMotors start");
        for (Object mObj : JSONMotors) {
            JSONObject mJSON = (JSONObject) mObj;
            String motorName = (String) mJSON.get("name");
            double minPower = (Double) mJSON.get("minPower");
            double maxPower = (Double) mJSON.get("maxPower");
            DcMotor dcParent = hardwareMap.dcMotor.get(motorName);
            DcMotor.Direction d = stringToMotorDirection((String) mJSON.get("direction"));
            Motor m = new Motor(dcParent, d, minPower, maxPower);
            dcParent.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            dcParent.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            motorMap.put(motorName, m);
        }
        telemetry.addData("configureMotors","configureMotors finish");
    }

    @Override
    private void configureServos(JSONArray JSONServos) {
        telemetry.addData("configureServos","configureServos start");
        for (Object sObj : JSONServos) {
            JSONObject sJSON = (JSONObject) sObj;
            String servoName = (String) sJSON.get("name");
            double minPosition = (Double) sJSON.get("minPosition");
            double maxPosition = (Double) sJSON.get("maxPosition");
            Servo sParent = hardwareMap.servo.get(servoName);
            Servo.Direction d = stringToServoDirection((String) sJSON.get("direction"));
            rServo s = new rServo(sParent, d, minPosition, maxPosition);
            servoMap.put(servoName, s);
        }
        telemetry.addData("configureServos","configureServos finish");
    }

    @Override
    private DcMotor.Direction stringToMotorDirection(String stringD) { //ToDo check if valueOf works as expected
        telemetry.addData("stringToMotorDirection","stmd start");
        if (stringD == "FORWARD") {
            return DcMotor.Direction.FORWARD;
        } else if (stringD == "REVERSE") {
            return DcMotor.Direction.REVERSE;
        } else {
            return DcMotor.Direction.valueOf(stringD);
        }
    }

    @Override
    private Servo.Direction stringToServoDirection(String stringD) {
        telemetry.addData("stringToServoDirection","stsd start");
        if (stringD == "FORWARD") {
            return Servo.Direction.FORWARD;
        } else if (stringD == "REVERSE") {
            return Servo.Direction.REVERSE;
        } else {
            return Servo.Direction.valueOf(stringD);
        }
    }

}
