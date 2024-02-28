package com.example.mapdmeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.maps.GeoApiContext;


@Configuration
public class GoogleMapConfig {
    @Bean
    public GeoApiContext geoApiContext(){
        return new GeoApiContext.Builder()
                .apiKey("YOUR_API_KEY")
                .build();
    }
}
