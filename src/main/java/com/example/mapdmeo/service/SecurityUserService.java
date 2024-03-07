package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.SecurityUser;
import com.example.mapdmeo.repo.SecurityUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserService {
    private final SecurityUserRepo securityUserRepo;

    public List<SecurityUser> getAllUsers(){
        return securityUserRepo.findAll();
    }
    public SecurityUser saveSecurityUser(SecurityUser securityUser){
        return securityUserRepo.save(securityUser);
    }

    public SecurityUser findByUsername(String name){
        return securityUserRepo.findByName(name)
                .orElseThrow(()-> new IllegalArgumentException("User not found with username " + name));
    }

}
