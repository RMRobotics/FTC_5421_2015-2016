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

    protected void configureHardware(String pathName) throws IOException {
        String configSource;
            configSource = readFile(pathName, Charset.defaultCharset());


        JSONParser jsonParser =  new JSONParser();
        String s = "";
        System.out.println(s);

        JSONObject ja = null;
        try {
            ja = (JSONObject) jsonParser.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(ja.toJSONString());

        JSONArray ja2 = (JSONArray) ja.get("motors");
        for(Object o : ja2) {
            JSONObject jo = (JSONObject) o;
            System.out.println(jo.get("name"));
            System.out.println(jo.get("minPower"));
            System.out.println(jo.get("maxPower"));
            System.out.println(jo.get("direction"));
            System.out.println();
        }

    }

    protected abstract String setConfigurationPath();

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
