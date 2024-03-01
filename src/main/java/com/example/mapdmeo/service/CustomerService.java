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
public class CustomerService implements CustomerRange{
    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;

    public List<Customer> findAllCustomer() {
        return customerRepo.findAll();
    }


    public Customer saveCustomer(Customer customer){
       return customerRepo.save(customer);
    }

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

        Optional<Customer> optionalCustomer = customerRepo.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Address> addresses = customer.getAddresses();
            return addresses;
        } else {
            return Collections.emptyList();
        }

    }


    @Override
    public List<Customer> findCustomerInRange(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        return customerRepo.findCustomerInRange(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }
}
