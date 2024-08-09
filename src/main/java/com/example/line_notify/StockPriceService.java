package com.example.line_notify;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class StockPriceService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private static final String API_URL = "https://www.alphavantage.co/query";

    public String getStockPrices(String[] symbols) {
        StringBuilder prices = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();

        for (String symbol : symbols) {
            String url = API_URL + "?function=TIME_SERIES_INTRADAY&symbol=" + symbol +
                    "&interval=1min&apikey=" + apiKey;

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String price = parseStockPrice(response.getBody(), symbol);

            prices.append(symbol).append(": ").append(price).append("\n");
        }

        return prices.toString();
    }

    private String parseStockPrice(String responseBody, String symbol) {
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONObject timeSeries = json.getJSONObject("Time Series (1min)");
            String latestTime = timeSeries.keys().next(); // Get the latest time key
            JSONObject latestData = timeSeries.getJSONObject(latestTime);
            String price = latestData.getString("4. close"); // Close price

            return price;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching price for " + symbol;
        }
    }
}
