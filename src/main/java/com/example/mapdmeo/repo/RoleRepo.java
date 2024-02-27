package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findRoleByRoleName(String roleName);
}
