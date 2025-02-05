package com.example.demo.util;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteSaveRequest {
	private Integer travelTimeTransportation;
    private Integer travelTimeWalk;
    private Integer travelTime;
    private Integer transferCount;
    private Integer fare;
    private Integer teiki1;
    private Integer teiki3;
    private Integer teiki6;
    private String routeInfo;
    private String nearest_station;
    private String arrival_station;
}
