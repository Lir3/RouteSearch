package com.example.demo.util;

import java.util.List;
import java.util.Map;

public class FareUtil {
    public static String getFare(Map<String, Object> route) {
        if (route.containsKey("Price") && route.get("Price") instanceof List) {
            List<Map<String, Object>> prices = (List<Map<String, Object>>) route.get("Price");
            // FareSummaryを探す
            for (Map<String, Object> price : prices) {
                if ("FareSummary".equals(price.get("kind"))) {
                    return String.valueOf(price.get("Oneway"));
                }
            }
            // FareSummaryがない場合は最初の運賃を使用
            if (!prices.isEmpty() && prices.get(0).containsKey("Oneway")) {
                return String.valueOf(prices.get(0).get("Oneway"));
            }
        }
        return "不明";
    }
}
