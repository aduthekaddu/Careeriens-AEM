package com.careeriens.core.services;

public interface WeatherService {
  String getApiKey();
  String getWeatherData(String location);
}