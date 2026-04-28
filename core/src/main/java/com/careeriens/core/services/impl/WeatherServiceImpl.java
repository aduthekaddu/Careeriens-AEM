package com.careeriens.core.services.impl;

import com.careeriens.core.services.WeatherService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = WeatherService.class, immediate = true)
@Designate(ocd = WeatherServiceImpl.Configuration.class)
public class WeatherServiceImpl implements WeatherService {

    private String apiKey;
    private String apiBaseUrl;

    @ObjectClassDefinition(name = "Weather Service")
    public @interface Configuration {
        @AttributeDefinition(name = "API Key", description = "API key required for authentication")
        String apiKey() default "9lu8Ef4bGzZurteKMQQNg3kyqkDdXU5n";

        @AttributeDefinition(name = "API Base URL")
        String apiBaseUrl() default "https://api.tomorrow.io/v4/weather/forecast";
    }

    @Activate
    @Modified
    protected void activate(Configuration config) {
        if (config != null) {
            this.apiKey = config.apiKey();
            this.apiBaseUrl = config.apiBaseUrl();
        }
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getWeatherData(String location) {
        HttpURLConnection connection = null;
        try {
            String url = apiBaseUrl + "?location=" + location + "&apikey=" + apiKey;

            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                return "Failed";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            return sb.toString();

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}