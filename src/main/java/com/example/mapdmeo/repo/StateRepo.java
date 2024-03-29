package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepo extends JpaRepository<State,Integer> {
   List<State> findByName(String name);
}
