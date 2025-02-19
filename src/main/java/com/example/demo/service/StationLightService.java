package com.example.demo.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StationLightService {

    @Value("${api.key}")
    private String apiKey;

    private final String STATION_API_URL = "https://api.ekispert.jp/v1/json/station/light";
    private final String COURSE_API_URL = "https://api.ekispert.jp/v1/json/search/course/extreme";

    public Map<String, Object> getRoutes(String from, String to, String via) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String fromCode = getStationCode(from);
            String toCode = getStationCode(to);
            String viaCodes = getViaCodes(via);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(COURSE_API_URL)
                .queryParam("key", apiKey)
                .queryParam("viaList", fromCode + (viaCodes.isEmpty() ? "" : ":" + viaCodes) + ":" + toCode);

            String url = builder.toUriString();
            System.out.println("Course API request URL: " + url);

            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("経路の取得に失敗しました", e);
        }
    }

    private String getViaCodes(String via) {
        if (via == null || via.isEmpty()) {
            return "";
        }

        StringBuilder viaCodesBuilder = new StringBuilder();
        for (String stationName : via.split(",")) {
            if (!stationName.trim().isEmpty()) {
                if (viaCodesBuilder.length() > 0) {
                    viaCodesBuilder.append(":");
                }
                viaCodesBuilder.append(getStationCode(stationName.trim()));
            }
        }
        return viaCodesBuilder.toString();
    }

    private String getStationCode(String stationName) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(STATION_API_URL)
            .queryParam("key", apiKey)
            .queryParam("name", stationName)
            .queryParam("type", "train")
            .queryParam("nameMatchType", "partial");

        URI url = builder.encode().build().toUri();
        System.out.println("Station API request URL: " + url);

        try {
            String responseString = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseString);

            if (rootNode.has("ResultSet") && rootNode.get("ResultSet").has("Point")) {
                JsonNode pointNode = rootNode.get("ResultSet").get("Point");
                return pointNode.isArray() ? pointNode.get(0).get("Station").get("code").asText()
                                           : pointNode.get("Station").get("code").asText();
            }
            throw new RuntimeException("駅が見つかりません: " + stationName);
        } catch (Exception e) {
            throw new RuntimeException("駅コードの取得に失敗しました: " + stationName, e);
        }
    }
}
