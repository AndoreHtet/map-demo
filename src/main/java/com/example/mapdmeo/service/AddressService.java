package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.repo.AddressRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;


    public List<Address> findAddressesByCustomerId(Long customerId) {
        return addressRepo.getAddressesByCustomerId(customerId);
    }


}
