package com.example.mapdmeo.controller;

import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;
    @GetMapping({"/states","/"})
    public String findByName(@RequestParam(required = false) String name, Model model){
        List<State> allStates = stateService.findAllStates();
        List<State> filterState;

        if ( name == null ||name.isEmpty()){
            filterState = allStates;
        }else {
            filterState = stateService.findByName(name);
        }
        model.addAttribute("allStates",allStates);
        model.addAttribute("filteredStates",filterState);
        return "states";
    }
    @GetMapping("/map")
    public String showMap(Model model){
        model.addAttribute("apiKey","AIzaSyBkMUEDzZ_3CHXPpxdzMriVb7a8avF0Wfc");
        return "states";
    }
}
