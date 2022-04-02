package com.oda.service.Impl;

import com.oda.component.CustomUserDetails;
import com.oda.model.User;
import com.oda.repo.comonuser.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public LoginDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new CustomUserDetails(user);
        }
    }
}
