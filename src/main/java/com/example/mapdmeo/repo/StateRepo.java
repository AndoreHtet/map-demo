package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepo extends JpaRepository<State,Integer> {
   List<State> findByName(String name);
}
