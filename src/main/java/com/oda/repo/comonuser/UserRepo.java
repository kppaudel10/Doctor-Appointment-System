package com.oda.repo.comonuser;

import com.oda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    public User findUserByEmail(String email);
}
