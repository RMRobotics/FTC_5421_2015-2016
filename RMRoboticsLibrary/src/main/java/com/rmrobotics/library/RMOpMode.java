package com.rmrobotics.library;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public abstract class RMOpMode extends OpMode {

    protected Map<String, Motor> motorMap;

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
    }

    @Override
    public void loop(){
        this.updateInput();
        this.calculate();
        this.updateHardware();
    }

    protected abstract void updateInput();

    protected abstract void calculate();

    protected abstract void updateHardware();

    protected abstract String setConfigurationPath();

    protected void configureHardware(String pathName) throws IOException, ParseException {
        String configSource = readFile(pathName, Charset.defaultCharset());
        JSONParser jsonParser =  new JSONParser();
        JSONObject jsonFile = (JSONObject) jsonParser.parse(configSource);
        JSONArray jsonMotors = (JSONArray) jsonFile.get("motors");
        this.configureMotors(jsonMotors);
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

    private DcMotor.Direction stringToDirection(String stringD){ //ToDo check if valueOf works as expected
        if(stringD == "forward"){
            return DcMotor.Direction.FORWARD;
        }else if(stringD == "reverse"){
            return DcMotor.Direction.REVERSE;
        }else{
            return DcMotor.Direction.valueOf(stringD);
        }
    }

}
