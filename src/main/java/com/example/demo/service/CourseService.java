// CourseService.java
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CourseResponse;

@Service
public class CourseService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public CourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CourseResponse searchCourse(String viaList) {
        String url = baseUrl + "/v1/json/search/course/extreme?key=" + apiKey + "&viaList=" + viaList;
        return restTemplate.getForObject(url, CourseResponse.class);
    }
}