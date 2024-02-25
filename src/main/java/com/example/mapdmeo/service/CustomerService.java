package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.entity.Customer;
import com.example.mapdmeo.repo.AddressRepo;
import com.example.mapdmeo.repo.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;

    public List<Customer> findAllCustomer() {
        return customerRepo.findAll();
    }

    //    public List<Customer> searchCustomers(String keyword){
//        return customerRepo.searchCustomers(keyword);
//    }
    public Customer findCustomerById(Long id) {
        return customerRepo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Customer> findByCustomerName(String name) {
        return customerRepo.findByName(name);
    }

    public List<Customer> findCustomersByNameContaining(String searchTerm) {
        return customerRepo.findByNameContainingIgnoreCase(searchTerm);
    }

    public List<Address> findCustomerAddresses(Long id) {
        // Retrieve the customer by ID
        Optional<Customer> optionalCustomer = customerRepo.findById(id);

        // Check if the customer exists
        if (optionalCustomer.isPresent()) {
            // Get the customer entity
            Customer customer = optionalCustomer.get();

            // Retrieve the addresses associated with the customer
            List<Address> addresses = customer.getAddresses();

            // Return the addresses
            return addresses;
        } else {
            // Customer not found, return an empty list or handle the case as needed
            return Collections.emptyList();
        }


    }
}
