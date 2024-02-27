package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Role;
import com.example.mapdmeo.entity.SecurityUser;
import com.example.mapdmeo.repo.RoleRepo;
import com.example.mapdmeo.repo.SecurityUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final RoleRepo roleRepo;
    private final SecurityUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SecurityUser securityUser){
        Role role =roleRepo.findRoleByRoleName("ADMIN");
        securityUser.addRole(role);
        securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
        userRepo.save(securityUser);
    }

}
