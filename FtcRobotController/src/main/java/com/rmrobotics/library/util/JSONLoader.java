package com.rmrobotics.library.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import com.rmrobotics.library.hardware.MOTOR_TYPE;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONLoader {

    private String filePath;
    private String JSONString;
    private Map<String, Motor> motorImportMap = new HashMap<String, Motor>();
    private Map<String, rServo> servoImportMap =  new HashMap<String, rServo>();
    private final HardwareMap hardwareMap;

    public JSONLoader(String path, final HardwareMap hMap) throws IOException, ParseException {
        filePath = path;
        hardwareMap = hMap;
        JSONString = FileUtils.readFileToString(new File(filePath));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(JSONString);
        JSONArray jsonMotors = (JSONArray) jsonFile.get("motors");
        JSONArray jsonServos = (JSONArray) jsonFile.get("servos");
        configureMotors(jsonMotors);
        configureServos(jsonServos);
    }

    private void configureMotors(JSONArray JSONMotors) {
        for (Object mObj : JSONMotors) {
            JSONObject mJSON = (JSONObject) mObj;
            String motorName = (String) mJSON.get("name");
            double minPower = (Double) mJSON.get("minPower");
            double maxPower = (Double) mJSON.get("maxPower");
            DcMotor dcParent = hardwareMap.dcMotor.get(motorName);
            DcMotor.Direction d = stringToMotorDirection((String) mJSON.get("direction"));
            MOTOR_TYPE mT = stringToMotorType((String) mJSON.get("motorType"));
            Motor m = new Motor(dcParent, d, minPower, maxPower, mT);
            motorImportMap.put(motorName, m);
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
            servoImportMap.put(servoName, s);
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

    private MOTOR_TYPE stringToMotorType(String stringD) {
        if (stringD.equals("NVRST_20")) {
            return MOTOR_TYPE.NVRST_20;
        } else if (stringD.equals("NVRST_40")) {
            return MOTOR_TYPE.NVRST_40;
        } else if (stringD.equals("NVRST_60")) {
            return MOTOR_TYPE.NVRST_60;
        } else {
            return MOTOR_TYPE.TETRIX;
        }
    }

    public Map<String,Motor> getMotorMap() {
        return motorImportMap;
    }

    public Map<String, rServo> getServoMap() {
        return servoImportMap;
    }
}
