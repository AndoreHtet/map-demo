package com.example.mapdmeo.util;

import com.google.maps.model.LatLng;

public class GeoUtils {
    private static final double EARTH_RADIUS_KM = 6371.0;

    // Calculate dynamic radius based on latitude and longitude
    public double calculateDynamicRadius(LatLng targetLatLng) {
        double latitude = targetLatLng.lat;
        double longitude = targetLatLng.lng;

        // Calculate the distance between two points (latitude, longitude) and (latitude + 1 degree, longitude)
        // This represents the distance of one degree latitude at the given longitude
        double lat1 = Math.toRadians(latitude);
        double lon1 = Math.toRadians(longitude);
        double lat2 = Math.toRadians(latitude + 1.0);
        double lon2 = Math.toRadians(longitude);
        double distanceOneDegreeLat = haversine(lat1, lon1, lat2, lon2);

        // The dynamic radius is the distance of one degree latitude at the given longitude
        return distanceOneDegreeLat;
    }

    // Haversine formula to calculate the distance between two points on the Earth's surface
    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
