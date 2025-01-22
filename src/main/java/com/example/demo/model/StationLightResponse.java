package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StationLightResponse {

    @JsonProperty("ResultSet")
    ResultSet resultSet;
    
    @Data
    public static class ResultSet {
        @JsonProperty("apiVersion")
        String apiVersion;
        @JsonProperty("engineVersion")
        String engineVersion;
        @JsonProperty("Point")
        List<Point> point;
    }

    @Data
    public static class Point {
        @JsonProperty("Station")
        Station station;
        @JsonProperty("Prefecture")
        Prefecture prefecture;
    }

    @Data
    public static class Station {
        @JsonProperty("code")
        String code;
        @JsonProperty("Name")
        String Name;
        @JsonProperty("Type")
        String Type;
        @JsonProperty("Yomi")
        String Yomi;
    }

    @Data
    public static class Prefecture {
        @JsonProperty("code")
        String code;
        @JsonProperty("Name")
        String Name;
    }
}