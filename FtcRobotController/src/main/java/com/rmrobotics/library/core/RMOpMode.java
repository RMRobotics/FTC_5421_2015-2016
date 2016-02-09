package com.rmrobotics.library.core;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.rmrobotics.library.control.Control;
import com.rmrobotics.library.hardware.Motor;
import com.rmrobotics.library.hardware.rServo;
import com.rmrobotics.library.util.JSONLoader;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class RMOpMode extends OpMode {

    protected Map<String, Motor> motorMap = new HashMap<String, Motor>();
    protected Map<String, rServo> servoMap =  new HashMap<String, rServo>();
    protected Control control;
    private String configPath;


    @Override
    public void init() {
        configPath =  setConfigurationPath();
        configureHardware(configPath);

        this.control = new Control(gamepad1, gamepad2);

        for (Motor m : motorMap.values()) {
            m.resetEncoder();
        }

        for (rServo r : servoMap.values()) { //testing only
            r.setInitPos();
        }
    }

    @Override
    public void loop() {
        this.updateInput();
        this.calculate();
        this.updateHardware();
    }

    protected void updateInput() {
        control.update(gamepad1, gamepad2); //TODO put in teleOp subclass
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

    private void configureHardware(String path){
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

}