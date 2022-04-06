package com.oda.repo.doctor;

import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    @Query(value = "select * from oda_doctor d where d.email = ?1",nativeQuery = true)
    Doctor findPatientByEmail(String email);
}
