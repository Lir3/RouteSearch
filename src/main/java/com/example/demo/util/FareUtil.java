package com.example.demo.util;

import java.util.List;
import java.util.Map;

public class FareUtil {
    public static String getFare(Map<String, Object> route) {
        if (route.containsKey("Price") && route.get("Price") instanceof List) {
            List<Map<String, Object>> prices = (List<Map<String, Object>>) route.get("Price");

            int totalFare = 0;
            boolean fareFound = false;

            for (Map<String, Object> price : prices) {
                String kind = (String) price.get("kind");
                String type = (String) price.get("Type");

                if ("ChargeSummary".equals(kind) || "FareSummary".equals(kind)) {
                    totalFare += Integer.parseInt(String.valueOf(price.get("Oneway")));
                    fareFound = true;
                }
            }

            if (fareFound) {
                return String.valueOf(totalFare);
            }

            // FareSummaryや特急料金がない場合
            if (!prices.isEmpty() && prices.get(0).containsKey("Oneway")) {
                return String.valueOf(prices.get(0).get("Oneway"));
            }
        }
        return " - ";
    }

    public static String getTeikiPrice(Map<String, Object> route, String period) {
        if (route.containsKey("Price") && route.get("Price") instanceof List) {
            List<Map<String, Object>> prices = (List<Map<String, Object>>) route.get("Price");

            for (Map<String, Object> price : prices) {
                String kind = (String) price.get("kind");
                if ((period + "Summary").equals(kind)) {
                    return String.valueOf(price.get("Oneway"));
                }
            }
        }
        return " - ";
    }
}