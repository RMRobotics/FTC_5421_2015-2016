package com.rmrobotics.library.util;

/**
 * Created by Simon on 2/18/2016.
 */

public class JSON5421OLD {
    private final String CONFIGURATION = "{\n" +
            "  \"motors\":[\n" +
            "    {\n" +
            "      \"name\":\"mL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"motorType\":\"NVRST_40\"\n" +
            "    },\n" +
            "    {\n" +
            "     \"name\":\"mR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"motorType\":\"NVRST_40\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"eL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" +
            "    {\n" +
            "     \"name\":\"eR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" +
            /*"    {\n" +
            "      \"name\":\"wL\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"motorType\":\"NVRST_60\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"wR\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"motorType\":\"NVRST_0\"\n" +
            "    },\n" +*/
            "     {\n" +
            "      \"name\":\"h\",\n" +
            "      \"minPower\":0.1,\n" +
            "      \"maxPower\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"motorType\":\"NVRST_20\"\n" +
            "    },\n" +
            "  ],\n" +
            "  \"servos\":[\n" +
            "    {\n" +
            "      \"name\":\"bL\",\n" +
            "      \"minPosition\":0.0,\n" +
            "      \"maxPosition\":0.5,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"bR\",\n" +
            "      \"minPosition\":0.04,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":1.0\n" +
            "    },\n" +
            /*"    {\n" +
            "      \"name\":\"climbers\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.6\n" +
            "    }\n" +*/
            "    {\n" +
            "      \"name\":\"bucket\",\n" +
            "      \"minPosition\":0.01,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.5\n" +
            "    }\n" +
            "    {\n" +
            "      \"name\":\"aL\",\n" +
            "      \"minPosition\":0.0,\n" +
            "      \"maxPosition\":0.5,\n" +
            "      \"direction\":\"FORWARD\",\n" +
            "      \"init\":0.0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"aR\",\n" +
            "      \"minPosition\":0.04,\n" +
            "      \"maxPosition\":1.0,\n" +
            "      \"direction\":\"REVERSE\",\n" +
            "      \"init\":1.0\n" +
            "    },\n" +
            "  ]\n" +
            "}";

    public JSON5421OLD() {}

    public String CONFIGURATION() {
        return CONFIGURATION;
    }
}
