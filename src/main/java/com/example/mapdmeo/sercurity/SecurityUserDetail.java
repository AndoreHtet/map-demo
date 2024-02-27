package com.example.mapdmeo.sercurity;

import com.example.mapdmeo.entity.Guest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


public class SecurityUserDetail implements UserDetails {
    private final Guest guest;

    public SecurityUserDetail(Guest guest) {
        this.guest = guest;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return guest.getSecurityUser().getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return guest.getSecurityUser().getPassword();
    }

    @Override
    public String getUsername() {
        return guest.getSecurityUser().getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
