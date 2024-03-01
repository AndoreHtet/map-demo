package com.example.mapdmeo.controller;

import com.example.mapdmeo.repo.GuestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final GuestRepo guestRepo;
    @GetMapping("/user-detail")
    public String userDetail(Model model, Principal principal){
        System.out.println("Before");

        model.addAttribute("guest",guestRepo.findGuestBySecurityUserName(principal.getName()).orElseThrow());

        return "accountDetail";
    }


}
