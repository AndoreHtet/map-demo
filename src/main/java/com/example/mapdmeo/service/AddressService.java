package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.repo.AddressRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;

    public Address saveAddress(Address address){
       return addressRepo.save(address);
    }
}
