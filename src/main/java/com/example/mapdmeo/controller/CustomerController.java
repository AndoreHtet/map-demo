package com.example.mapdmeo.controller;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.entity.Customer;
import com.example.mapdmeo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping({"/customers","/showCustomers"})
    public String showCustomerList(Model model) {
        try {
            List<Customer> customers = customerService.findAllCustomer();
            model.addAttribute("customers", customers);
            return "customers";
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // You can return an error view here if needed
            return "error";
        }
    }

    @GetMapping("/customers/customer-list")
    @ResponseBody
    public ResponseEntity<List<Customer>> findAllCustomer(){
        try {
            List<Customer> customerList = customerService.findAllCustomer();
            return ResponseEntity.ok(customerList);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/customers/address-list")
    public ResponseEntity<List<Address>> getCustomerAddresses(@RequestParam Long customerId) {
        try {
            List<Address> addresses = customerService.findCustomerAddresses(customerId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/customer-detail")
    public String customerDetails(@RequestParam("id")Long id,Model model){
        model.addAttribute("customer",customerService.findCustomerById(id));
        return "customerDetail";
    }


    @GetMapping("/customers/autocomplete")
    @ResponseBody
    public ResponseEntity<List<Customer>> autocompleteCustomers(@RequestParam String searchTerm) {
        try {
            List<Customer> matchedCustomers = customerService.findCustomersByNameContaining(searchTerm);
            return ResponseEntity.ok(matchedCustomers);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/customers/found-customer")
    public ResponseEntity<List<Customer>> findCustomerByName(@RequestParam String name){
        try {
            List<Customer> foundCustomer = customerService.findByCustomerName(name);
            return ResponseEntity.ok(foundCustomer);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/customers/in-range")
    public ResponseEntity<List<Customer>> findCustomersInRange(@RequestParam Double latitude,@RequestParam Double longitude){
        try {
           double rangeInKilometers = 1.0;
           double rangeInDegrees = rangeInKilometers/111.32;

           double minLatitude = latitude - rangeInDegrees;
           double maxLatitude = latitude + rangeInDegrees;
           double minLongitude = longitude - rangeInDegrees;
           double maxLongitude = longitude + rangeInDegrees;

           List<Customer> customersInRange = customerService.findCustomerInRange(minLatitude,maxLatitude,minLongitude,maxLongitude);
            return ResponseEntity.ok(customersInRange);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }





}
