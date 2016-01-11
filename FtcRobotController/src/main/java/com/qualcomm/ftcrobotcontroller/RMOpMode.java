package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.control.Control;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


public abstract class RMOpMode extends OpMode {

    protected Map<String, Motor> motorMap = new HashMap<String, Motor>();
    protected Map<String, rServo> servoMap =  new HashMap<String, rServo>();
    protected Control control;
    public int opType;

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
        for (Motor m : motorMap.values()) {
            m.resetEncoder();
        }
        for (rServo r : servoMap.values()) { //testing only
            r.setInitPos();
        }
        telemetry.addData("init", "init start2");
    }

    @Override
    public void loop() {
        this.updateInput();
        this.calculate();
        this.updateHardware();
    }

    protected void updateInput() {
        control.update(gamepad1, gamepad2);
    }

    protected abstract void calculate();

    protected void updateHardware() {
        motorMap.get("DriveLeftTwo").setDesiredPower(motorMap.get("DriveLeftOne").getDesiredPower());
        motorMap.get("DriveRightTwo").setDesiredPower(motorMap.get("DriveRightOne").getDesiredPower());
        for (Motor m : motorMap.values()) {
            m.updateCurrentPower();
            m.setCurrentPower();
            if (opType == 0) {
                m.runToPosition();
            } else {
                m.runWithoutEncoders();
            }
        }
        for (rServo s : servoMap.values()) {
            s.updateCurrentPosition();
            s.setPosition();
        }
    }

    protected abstract String setConfigurationPath();

    protected void configureHardware(String pathName) throws IOException, ParseException {
        String configSource = readFile(pathName, Charset.defaultCharset());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(configSource);
        JSONArray jsonMotors = (JSONArray) jsonFile.get("motors");
        JSONArray jsonServos = (JSONArray) jsonFile.get("servos");
        this.configureMotors(jsonMotors);
        this.configureServos(jsonServos);
        //Todo add methods for configuring and sensors
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        /*byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
        */
        return path;
    }

    private void configureMotors(JSONArray JSONMotors) {
        for (Object mObj : JSONMotors) {
            JSONObject mJSON = (JSONObject) mObj;
            String motorName = (String) mJSON.get("name");
            double minPower = (Double) mJSON.get("minPower");
            double maxPower = (Double) mJSON.get("maxPower");
            DcMotor dcParent = hardwareMap.dcMotor.get(motorName);
            DcMotor.Direction d = stringToMotorDirection((String) mJSON.get("direction"));
            Motor m = new Motor(dcParent, d, minPower, maxPower);
            motorMap.put(motorName, m);
        }
    }

    private void configureServos(JSONArray JSONServos) {
        for (Object sObj : JSONServos) {
            JSONObject sJSON = (JSONObject) sObj;
            String servoName = (String) sJSON.get("name");
            double minPosition = (Double) sJSON.get("minPosition");
            double maxPosition = (Double) sJSON.get("maxPosition");
            Servo sParent = hardwareMap.servo.get(servoName);
            Servo.Direction d = stringToServoDirection((String) sJSON.get("direction"));
            double init = (Double) sJSON.get("init");
            rServo s = new rServo(sParent, d, minPosition, maxPosition, init);
            servoMap.put(servoName, s);
        }
    }

    private DcMotor.Direction stringToMotorDirection(String stringD) { //ToDo check if valueOf works as expected
        if (stringD.equals("FORWARD")) {
            return DcMotor.Direction.FORWARD;
        } else if (stringD.equals("REVERSE")) {
            return DcMotor.Direction.REVERSE;
        } else {
            return DcMotor.Direction.valueOf(stringD);
        }
    }

    private Servo.Direction stringToServoDirection(String stringD) {
        if (stringD.equals("FORWARD")) {
            return Servo.Direction.FORWARD;
        } else if (stringD.equals("REVERSE")) {
            return Servo.Direction.REVERSE;
        } else {
            return Servo.Direction.valueOf(stringD);
        }
    }
}