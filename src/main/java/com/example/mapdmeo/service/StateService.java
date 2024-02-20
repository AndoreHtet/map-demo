package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.repo.StateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepo stateRepo;

    public List<State> findByName(String name){
        return stateRepo.findByName(name);
    }

    public List<State> findAllStates(){
        return stateRepo.findAll();
    }
}
