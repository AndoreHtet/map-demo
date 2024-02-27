package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepo extends JpaRepository<SecurityUser,Integer> {
    Optional<SecurityUser>findByName(String username);
}
