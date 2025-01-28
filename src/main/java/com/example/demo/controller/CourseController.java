package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.StationLightService;

@Controller
public class CourseController {

    private final StationLightService stationLightService;

    public CourseController(StationLightService stationLightService) {
        this.stationLightService = stationLightService;
    }

    @GetMapping("/map")
    public String index() {
        return "map";
    }

    @PostMapping("/search")
    public String searchCourse(@RequestParam String from, @RequestParam String to,
                               @RequestParam(name = "via[]", required = false) List<String> via, Model model) {
        String viaString = via != null ? String.join(",", via) : "";
        Map<String, Object> response = stationLightService.getRoutes(from, to, viaString);
        model.addAttribute("course", response);
        System.out.println("これだぁぁっぁぁぁ"+response);
        return "result";
    }
}