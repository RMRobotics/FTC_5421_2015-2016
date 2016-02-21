package com.rmrobotics.library.core;

/**
 * Created by Simon on 2/17/2016.
 */
public abstract class RMAutoMode extends RMOpMode {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        calculate();
        updateHardware();
    }

    @Override
    protected abstract void calculate();

    @Override
    protected void updateHardware() {
        super.updateHardware();
    }

    @Override
    protected abstract String setConfigurationPath();

    @Override
    protected void configureHardware(String path) {
        super.configureHardware(path);
    }
}
