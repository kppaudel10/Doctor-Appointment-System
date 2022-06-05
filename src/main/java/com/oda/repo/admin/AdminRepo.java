package com.oda.repo.admin;

import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    @Query(value = "select * from oda_admin d where d.email = ?1",nativeQuery = true)
    Admin findPatientByEmail(String email);

    @Query(value = "select * from oda_admin d where d.email = ?1 or d.mobile_number = ?1",nativeQuery = true)
    Admin findByUserName(String username);

    @Query(value = "select count (id) from oda_admin where email = ?1",nativeQuery = true)
    Integer getEmailCount(String email);

}
