package com.rmrobotics.library.util;

/**
 * Created by Josh on 2/18/2016.
 */
public class JSON8121 {
        public final String CONFIGURATION = "{\n" +
                "  \"motors\":[\n" +
                "    {\n" +
                "      \"name\":\"MotorL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\":\"MotorR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendL\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\":\"MotorExtendR\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"MotorExtendC\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"REVERSE\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"Harvester\",\n" +
                "      \"minPower\":0.1,\n" +
                "      \"maxPower\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"motorType\":\"NVRST_40\"\n" +
                "    },\n" +
                "  ],\n" +
                "  \"servos\":[\n" +
                "    {\n" +
                "      \"name\":\"BucketSeeSaw\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"BucketLeft\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    },\n" +
                "     {\n" +
                "      \"name\":\"BucketRight\",\n" +
                "      \"minPosition\":0.01,\n" +
                "      \"maxPosition\":1.0,\n" +
                "      \"direction\":\"FORWARD\",\n" +
                "      \"init\":0.6\n" +
                "    } \n" +
                "  ],\n" +
                "}";

        public JSON8121() {}

        public String CONFIGURATION() {
                return CONFIGURATION;
        }
}
