package com.oda.repo.patient;

import com.oda.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    @Query(value = "select * from oda_patient p where p.email = ?1",nativeQuery = true)
    Patient findPatientByEmail(String email);
}
