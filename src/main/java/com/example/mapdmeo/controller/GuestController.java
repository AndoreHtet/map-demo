package com.example.mapdmeo.controller;


import com.example.mapdmeo.entity.Guest;
import com.example.mapdmeo.entity.Role;
import com.example.mapdmeo.entity.SecurityUser;
import com.example.mapdmeo.repo.RoleRepo;
import com.example.mapdmeo.repo.SecurityUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserRepo securityUserRepo;
    @PostMapping("/save-guest")
    @Transactional
    public String saveGuest(SecurityUser securityUser,
                            @RequestParam String firstName,
                            @RequestParam String lastName){

        Guest guest = new Guest(firstName,lastName);
        securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
        Role role = roleRepo.findRoleByRoleName("USER");
        securityUser.addRole(role);
        securityUser.setGuest(guest);
        guest.setSecurityUser(securityUser);
        securityUserRepo.save(securityUser);

        return "login";
    }
}
