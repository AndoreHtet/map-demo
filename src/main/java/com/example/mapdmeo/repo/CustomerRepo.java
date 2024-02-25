package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    List<Customer> findByName(String name);

    List<Customer> findByNameContainingIgnoreCase(String searchTerm);
}
