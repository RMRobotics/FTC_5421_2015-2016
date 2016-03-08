package com.rmrobotics.library.util;


/**
 * Created by Peter on 3/8/16.
 */
public class JSONGYRO {
    public final String CONFIGURATION = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"MotorL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" +
            "    {\n" +
            "     \"name\":\"MotorR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" +
            "   ],\n" +
            " }";

        public JSONGYRO() {}

        public String CONFIGURATION() {
                return CONFIGURATION;
        }
}
}
