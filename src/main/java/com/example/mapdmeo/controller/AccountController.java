package com.example.mapdmeo.controller;

import com.example.mapdmeo.entity.SecurityUser;
import com.example.mapdmeo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security")
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("user",new SecurityUser());
        return "signUp";
    }


    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }
}
