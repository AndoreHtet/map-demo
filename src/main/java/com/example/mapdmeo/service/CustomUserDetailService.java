package com.example.mapdmeo.service;

import com.example.mapdmeo.repo.GuestRepo;
import com.example.mapdmeo.sercurity.SecurityUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final GuestRepo guestRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return guestRepo
                .findGuestBySecurityUserName(username)
                    .map(SecurityUserDetail::new)
                .orElseThrow(()-> new UsernameNotFoundException("Not Found!"));
    }
}
