package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

    List<Address> getAddressesByCustomerId(Long customerId);

}
