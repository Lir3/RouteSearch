// CourseResponse.java
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseResponse {
    @JsonProperty("ResultSet")
    private ResultSet resultSet;

    @Data
    public static class ResultSet {
        private String apiVersion;
        private String engineVersion;
        private Course[] course;
    }

    @Data
    public static class Course {
        private String searchType;
        private String dataType;
        private String serializeData;
        private Price[] price;
        private Route route;
    }

    @Data
    public static class Price {
        private String kind;
        private String oneway;
        private String round;
    }

    @Data
    public static class Route {
        private int timeOther;
        private int timeOnBoard;
        private String exhaustCO2;
        private String distance;
        private int timeWalk;
        private String transferCount;
        private Line[] line;
        private Point[] point;
    }

    @Data
    public static class Line {
        private String name;
        private String type;
        private String color;
    }

    @Data
    public static class Point {
        private Station station;
    }

    @Data
    public static class Station {
        private String code;
        private String name;
        private String type;
    }
}