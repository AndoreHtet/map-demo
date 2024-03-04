package com.example.mapdmeo.repo;

import com.example.mapdmeo.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRepo extends JpaRepository<SecurityUser,Integer> {

    @Query("SELECT u FROM SecurityUser u WHERE u.name = :name")
    public SecurityUser getSecurityUserByUsername(@Param("name")String name);
}
