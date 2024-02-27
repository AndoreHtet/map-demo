package com.example.mapdmeo.controller;

import com.example.mapdmeo.config.GoogleMapConfig;
import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;
    private final GoogleMapConfig googleMapConfig;
    @GetMapping("/states")
    public String findAllStatesAndView(@RequestParam(required = false) String name, Model model){
        List<State> allStates = stateService.findAllStates();
        List<State> filteredStates;

        if (name == null || name.isEmpty()){
            filteredStates = allStates;
        } else {
            filteredStates = stateService.findByName(name);
        }
        model.addAttribute("allStates", allStates);
        model.addAttribute("filteredStates", filteredStates);
        return "states";
    }


    @GetMapping("/states/all")
    @ResponseBody
    public ResponseEntity<List<State>> findAllStates() {
        List<State> allStates = stateService.findAllStates();
        return ResponseEntity.ok(allStates);
    }

    @GetMapping("/states/filter")
    @ResponseBody
    public ResponseEntity<List<State>> findFilteredStates(@RequestParam String name) {
        List<State> filteredStates = stateService.findByName(name);
        return ResponseEntity.ok(filteredStates);
    }
    @GetMapping("/map")
    public String showMap(Model model){
        model.addAttribute("apiKey",googleMapConfig.geoApiContext());
        return "map";
    }
}
