package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    List<Customer> findByName(String name);

    List<Customer> findByNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT c FROM Customer c JOIN c.addresses a WHERE a.latitude BETWEEN :minLatitude AND :maxLatitude AND a.longitude BETWEEN :minLongitude AND :maxLongitude")
    List<Customer> findCustomerInRange(@Param("minLatitude") double minLatitude,@Param("maxLatitude") double maxLatitude,
                                       @Param("minLongitude") double minLongitude,@Param("maxLongitude")double maxLongitude);
}
