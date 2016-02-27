package com.rmrobotics.library.core;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;
import com.rmrobotics.library.util.JSON8121;
import com.rmrobotics.library.util.JSONLoader;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class RMOpMode extends OpMode {

    protected Map<String, Motor> motorMap = new HashMap<String, Motor>();
    protected Map<String, rServo> servoMap =  new HashMap<String, rServo>();

    private Map<String, Motor> motorImportMap = new HashMap<String, Motor>();
    protected Control control;
    private String configPath;
    protected int team;

    public String life;

    @Override
    public void init() {
        if (team != 5421 && team != 8121) {
            configPath = setConfigurationPath();
            configureHardware(configPath);
        } else {
            configPath = setConfigurationPath();
            configureHardware();
        }

        this.control = new Control(gamepad1, gamepad2);

        for (Motor m : motorMap.values()) {
            //m.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            m.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }

        for (rServo r : servoMap.values()) { //testing only
            r.setInitPos();
        }
    }

    public void init(int n) {
        this.control = new Control(gamepad1, gamepad2);
    }

    @Override
    public void loop() {
        this.updateInput();
        this.calculate();
        this.updateHardware();
    }

    protected void updateInput() {
        try {
            control.update(gamepad1, gamepad2); //TODO put in teleOp subclass
        } catch (NullPointerException e) {
            telemetry.addData("Oops","oops twice");
        }

    }

    protected abstract void calculate();

    protected void updateHardware() {
        for (Motor m : motorMap.values()) {
            m.updateCurrentPower();
            m.setCurrentPower();
        }
        for (rServo s : servoMap.values()) {
            s.updateCurrentPosition();
            s.setPosition();
        }
    }

    protected abstract String setConfigurationPath();

    protected void configureHardware(String path){
        JSONLoader jsonLoader = null;
        try {
            jsonLoader = new JSONLoader(path, hardwareMap);
        } catch (IOException e) {
            e.printStackTrace();
            DbgLog.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            DbgLog.error(e.getMessage());
        }
        motorMap =  jsonLoader.getMotorMap();
        servoMap =  jsonLoader.getServoMap();
    }

    protected void configureHardware() {
        JSONLoader jsonLoader = null;
        try {
            jsonLoader = new JSONLoader(team, hardwareMap);
        } catch (IOException e) {
            e.printStackTrace();
            DbgLog.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            JSON8121 config8121 = new JSON8121();
            telemetry.addData("PARSE", "ARGHHH");
            telemetry.addData("File", config8121.CONFIGURATION());
            DbgLog.error(e.getMessage());
        } catch (NullPointerException e){
            telemetry.addData("Null","null");
        }
        try {
                    motorMap = jsonLoader.getMotorMap();
        } catch (NullPointerException er){
            telemetry.addData("OnlyAtMotorMap", "OnlyatMotorMap");
        }
        try{
            servoMap = jsonLoader.getServoMap();
        }catch (NullPointerException err){
            telemetry.addData("SERVO","SERVO");
        }

    }

    protected void setTeam(int tnumber) {
        team = tnumber;
    }

}