package com.pickleball.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={API_KEY}&units=metric";

    public String getWeatherForecast(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{city}", city).replace("{API_KEY}", "your_api_key_here");
        return restTemplate.getForObject(url, String.class);
    }
}
