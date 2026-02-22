package com.expensetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;

@Service
public class CurrencyService {

    private final String API_URL = "https://open.er-api.com/v6/latest/";
    private final RestTemplate restTemplate = new RestTemplate();
    
    // Simple cache for exchange rates to INR
    private Map<String, BigDecimal> rateCache = new ConcurrentHashMap<>();
    private LocalDateTime lastCacheUpdate = LocalDateTime.MIN;

    public BigDecimal convertToINR(BigDecimal amount, String fromCurrency) {
        if ("INR".equalsIgnoreCase(fromCurrency)) {
            return amount;
        }

        BigDecimal rate = getRateToINR(fromCurrency);
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRateToINR(String fromCurrency) {
        if (fromCurrency == null || fromCurrency.trim().isEmpty()) return BigDecimal.ONE;
        String currency = fromCurrency.toUpperCase();
        
        refreshCacheIfNeeded();
        if (rateCache.containsKey(currency)) {
            return rateCache.get(currency);
        }

        try {
            Map<String, Object> response = restTemplate.getForObject(API_URL + currency, Map.class);
            if (response != null && ("success".equals(response.get("result")) || response.containsKey("rates"))) {
                Map<String, Object> rates = (Map<String, Object>) response.get("rates");
                if (rates != null && rates.containsKey("INR")) {
                    Object inrRate = rates.get("INR");
                    BigDecimal rate = new BigDecimal(inrRate.toString());
                    rateCache.put(currency, rate);
                    return rate;
                }
            }
        } catch (Exception e) {
            System.err.println("Currency API Error: " + e.getMessage());
            // Fallback rates if API fails
            if ("USD".equalsIgnoreCase(currency)) return new BigDecimal("83.20");
            if ("EUR".equalsIgnoreCase(currency)) return new BigDecimal("90.15");
            if ("GBP".equalsIgnoreCase(currency)) return new BigDecimal("105.30");
        }
        
        return BigDecimal.ONE; // Default fallback
    }

    private void refreshCacheIfNeeded() {
        if (lastCacheUpdate.isBefore(LocalDateTime.now().minusHours(12))) {
            rateCache.clear();
            lastCacheUpdate = LocalDateTime.now();
        }
    }
}
