package com.oda.repo.patient;

import com.oda.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Integer> {

}
