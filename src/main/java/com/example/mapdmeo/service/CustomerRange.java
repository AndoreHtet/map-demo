package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Customer;

import java.util.List;

public interface CustomerRange {

    List<Customer> findCustomerInRange(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);
}
