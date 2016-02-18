package com.rmrobotics.library.util;

/**
 * Created by Simon on 2/18/2016.
 */
public class JSON8121 {

    private final String CONFIGURATION = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"MotorL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorExtendL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"MotorExtendR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"MotorUpL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"MotorUpR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Winch\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "     {\n" +
            "      \"name\":\"Harvester\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"Bucket\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Latch\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"Climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.01,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"isContinuous\":\"no\",\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public JSON8121() {}

    public String CONFIGURATION() {
        return CONFIGURATION;
    }
}
