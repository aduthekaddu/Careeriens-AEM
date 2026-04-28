package com.careeriens.core.models;

import com.careeriens.core.services.WeatherService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WeatherModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherModel.class);

    @OSGiService
    private WeatherService weatherService;

    @ValueMapValue
    private String location;

    @ValueMapValue
    private String cityName;

    @ValueMapValue
    private Integer refreshInterval;

    @PostConstruct
    protected void init() {
        if (weatherService == null) {
            LOGGER.warn("WeatherService not injected into WeatherModel");
        }
    }

    public String getLocation() {
        return location != null ? location : "28.7041,77.1025";
    }

    public String getCityName() {
        return cityName != null ? cityName : "Delhi";
    }

    public int getRefreshInterval() {
        return (refreshInterval != null && refreshInterval > 10) ? refreshInterval : 30;
    }

    public String getApiKey() {
        return weatherService != null ? weatherService.getApiKey() : "";
    }
}