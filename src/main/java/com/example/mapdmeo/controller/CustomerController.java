package com.example.mapdmeo.controller;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.entity.Customer;
import com.example.mapdmeo.service.AddressService;
import com.example.mapdmeo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;


    @GetMapping({"/customers","/showCustomers"})
    public String showCustomerList(Model model) {
        try {
            List<Customer> customers = customerService.findAllCustomer();
            model.addAttribute("customers", customers);
            return "customers";
        } catch (Exception e) {
            e.printStackTrace();

            return "error";
        }
    }
    @PostMapping("/customers/save-customer")
    public String saveCustomer(@ModelAttribute("created_customer") Customer customer) {

       customerService.saveCustomer(customer);
       return "redirect:/customers/create-address";
    }


    @PostMapping("/customers/save-address")
    public String saveAddress(@ModelAttribute("address")Address addresses,@RequestParam("customerId") Long customerId){
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null){
            return "redirect:/error";
        }
        Address savedAddress = addressService.saveAddress(addresses);
        customer.addAddress(savedAddress);
        customerService.saveCustomer(customer);
        return "redirect:/showCustomers";
    }
    @GetMapping("/customers/create-address")
    public String createAddress(Model model){
        List<Customer> customers = customerService.findAllCustomer();
        model.addAttribute("customers",customers);
        model.addAttribute("address",new Address());
        return "addressForm";
    }


    @GetMapping("/customers/create-customer")
    public String showCustomerForm(Model model) {
        model.addAttribute("created_customer", new Customer());
        return "customerForm";
    }

    @GetMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id")Long id){

         return "redirect:/showCustomers";
    }


    @GetMapping("/customers/customer-list")
    @ResponseBody
    public ResponseEntity<List<Customer>> findAllCustomer(){
        try {
            List<Customer> customerList = customerService.findAllCustomer();
            return ResponseEntity.ok(customerList);
        } catch (Exception e) {

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

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/customers/in-range")
    public ResponseEntity<List<Customer>> findCustomersInRange(@RequestParam Double latitude,@RequestParam Double longitude){
        try {
           double rangeInKilometers = 1.1;
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

//      When you use this controller,you must need biilling for google api
//    @GetMapping("/customers/in-range")
//    public ResponseEntity<List<Customer>> findCustomersInRange(@RequestParam Double latitude,@RequestParam Double longitude){
//        try {
//            GeoApiContext context = new GeoApiContext.Builder().apiKey("Your_Api_Key").build();
//            GeocodingResult[] targetResults = GeocodingApi.geocode(context, latitude + "," + longitude).await();
//            if (targetResults.length == 0) {
//                // Handle error: Unable to geocode target location
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//            LatLng targetLatLng = targetResults[0].geometry.location;
//            double latDistanceOneDegree = 111.32; // Kilometers per degree of latitude
//            double lonDistanceOneDegree = 111.32 * Math.cos(Math.toRadians(targetLatLng.lat)); // Kilometers per degree of longitude
//
//            // Calculate the dynamic range based on target location
//            double dynamicRadius = 1.1; // Initial range in kilometers
//            double rangeInDegrees = dynamicRadius / latDistanceOneDegree; // Convert range to degrees
//
//            // Calculate range boundaries
//            double minLatitude = latitude - rangeInDegrees;
//            double maxLatitude = latitude + rangeInDegrees;
//            double minLongitude = longitude - rangeInDegrees / lonDistanceOneDegree;
//            double maxLongitude = longitude + rangeInDegrees / lonDistanceOneDegree;
//
//            // Find customers within the dynamic range
//            List<Customer> customersInRange = customerService.findCustomerInRange(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//            return ResponseEntity.ok(customersInRange);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//
//    }


}
