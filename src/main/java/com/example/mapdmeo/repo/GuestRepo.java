package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.Guest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GuestRepo extends CrudRepository<Guest,Integer> {
    Optional<Guest> findGuestBySecurityUserName(String name);
}
