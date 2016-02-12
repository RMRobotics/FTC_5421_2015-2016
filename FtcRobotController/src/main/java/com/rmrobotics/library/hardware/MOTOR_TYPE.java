package com.rmrobotics.library.hardware;

/**
 * Created by Simon on 2/8/2016.
 */
public enum MOTOR_TYPE {
    TETRIX("Tetrix"),
    NVRST_20("NeveRest 20"),
    NVRST_40("NeveRest 40"),
    NVRST_60("NeveRest 60");

    String motorTypeName;

    MOTOR_TYPE(String stringD) {
        this.motorTypeName = stringD;
    }

    public String toString() {
        return motorTypeName;
    }
}