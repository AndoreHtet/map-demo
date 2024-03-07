package com.example.mapdmeo.controller;

import com.example.mapdmeo.entity.Role;
import com.example.mapdmeo.entity.SecurityUser;
import com.example.mapdmeo.repo.GuestRepo;
import com.example.mapdmeo.service.RoleService;
import com.example.mapdmeo.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GuestRepo guestRepo;
    private final RoleService roleService;
    private final SecurityUserService securityUserService;
    @GetMapping("/user-detail")
    public String userDetail(Model model, Principal principal){
        String username = principal.getName();

        SecurityUser user = securityUserService.findByUsername(username);
        model.addAttribute("user",user);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roles",allRoles);

        model.addAttribute("guest",guestRepo.findGuestBySecurityUserName(principal.getName()).orElseThrow());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        model.addAttribute("isAdmin",isAdmin);
        return "accountDetail";
    }

    @GetMapping("/user-management")
    public String userManagement(Model model){


        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roles",allRoles);

        List<SecurityUser> users = securityUserService.getAllUsers();
        model.addAttribute("users",users);
        return "userManagement";
    }



}
