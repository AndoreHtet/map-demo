package com.example.mapdmeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/","/home"})
    public String homeview(){
        return "home";
    }

    @GetMapping("/map")
    public String showMap(Model model){
//        model.addAttribute("apiKey",googleMapConfig.geoApiContext());
        return "map";
    }
}
