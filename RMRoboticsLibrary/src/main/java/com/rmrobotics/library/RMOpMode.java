package com.rmrobotics.library;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.rmrobotics.library.control.Control;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;


public abstract class RMOpMode extends OpMode {

    protected Map<String, Motor> motorMap;
    protected Map<String, Servo> servoMap;
    protected Control control;

    @Override
    public void init() {
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
    }

    @Override
    public void loop(){
        this.updateInput();
        this.calculate();
        this.updateHardware();
    }

    protected void updateInput(){
        control.update(gamepad1, gamepad2);
    }

    protected abstract void calculate();

    protected void updateHardware(){

    }

    protected abstract String setConfigurationPath();

    protected void configureHardware(String pathName) throws IOException, ParseException {
        String configSource = readFile(pathName, Charset.defaultCharset());
        JSONParser jsonParser =  new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(configSource);
        JSONArray jsonMotors = (JSONArray) jsonFile.get("motors");
        JSONArray jsonServos = (JSONArray) jsonFile.get("servos")
        this.configureMotors(jsonMotors);
        this.configureServos(jsonServos);
        //Todo add methods for configuring servos and sensors
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private void configureMotors(JSONArray JSONMotors){
        for(Object mObj : JSONMotors){
            JSONObject mJSON = (JSONObject)  mObj;
            String motorName = (String) mJSON.get("name");
            double minPower = (Double) mJSON.get("minPower");
            double maxPower = (Double) mJSON.get("maxPower");
            DcMotor dcParent = hardwareMap.dcMotor.get(motorName);
            DcMotor.Direction d = stringToDirection((String) mJSON.get("direction"));
            Motor m = new Motor(dcParent, d, minPower, maxPower);
            motorMap.put(motorName, m);
        }
    }

    private void configureServos(JSONArray JSONServos){
        for(Object sObj : JSONServos){
            JSONObject sJSON = (JSONObject)  sObj;
            String servoName = (String) sJSON.get("name");
            double minPosition = (Double) sJSON.get("minPosition");
            double maxPosition = (Double) sJSON.get("maxPosition");
            Servo sParent = hardwareMap.Servo.get(servoName);
            Servo.Direction d = stringToDirection((String) sJSON.get("direction"));
            boolean isContinuous = stringToBoolean((String) sJSON.get("isContinuous"));
            rServo s = new rServo(sParent, d, minPosition, maxPosition, isContinuous);
            servoMap.put(servoName, s);
        }
    }

    private DcMotor.Direction stringToDirection(String stringD){ //ToDo check if valueOf works as expected
        if(stringD == "forward"){
            return DcMotor.Direction.FORWARD;
        }else if(stringD == "reverse"){
            return DcMotor.Direction.REVERSE;
        }else{
            return DcMotor.Direction.valueOf(stringD);
        }
    }

    private boolean stringToBoolean(String stringC){
        if(stringC == "true"){
            return true;
        }else if(stringC == "false"){
            return false;
        }else{
            return Boolean.parseBoolean(null);
        }
    }

}
