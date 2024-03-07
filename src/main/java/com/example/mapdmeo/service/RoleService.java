package com.example.mapdmeo.service;

import com.example.mapdmeo.entity.Role;
import com.example.mapdmeo.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public List<Role> getAllRoles(){
        return roleRepo.findAll();
    }

    public List<Role> getRolesByIds(List<Integer> roleIds){
        return roleRepo.findAllById(roleIds);
    }
}
