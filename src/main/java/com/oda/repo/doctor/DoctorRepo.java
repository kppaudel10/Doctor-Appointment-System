package com.oda.repo.doctor;

import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    @Query(value = "select * from oda_doctor d where d.email = ?1",nativeQuery = true)
    Doctor findDoctorByEmail(String email);

    @Query(value = "select * from oda_doctor where name like ?1%",nativeQuery = true)
    List<Doctor> findDoctorByName(String name);

    @Query(value = "select * from oda_doctor d where d.email = ?1 or d.mobile_number = ?1",nativeQuery = true)
    Doctor findByUserName(String username);
}
