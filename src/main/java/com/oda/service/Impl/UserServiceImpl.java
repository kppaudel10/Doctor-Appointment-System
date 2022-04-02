package com.oda.service.Impl;

import com.oda.model.User;
import com.oda.repo.comonuser.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user){
       return userRepo.save(user);
    }
}
