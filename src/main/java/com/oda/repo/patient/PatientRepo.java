package com.oda.repo.patient;

import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

    @Query(value = "select * from oda_patient p where p.email = ?1",nativeQuery = true)
    Patient findPatientByEmail(String email);

    @Query(value = "select * from oda_patient d where d.email = ?1 or d.mobile_number = ?1",nativeQuery = true)
    Patient findByUserName(String username);

    @Query(value = "select * from oda_patient p where cast(p.mobile_number as char) like ?1 or p.email like ?1",nativeQuery = true)
    List<Patient> findPatientByEmailAndMobileNumber(String userInput);

    @Query(value = "select * from oda_patient p where p.mobile_number = ?1 or p.email = ?1",nativeQuery = true)
    public Patient findPatientByContactDetails(String contact);

    @Query(value = "select count(id) from oda_patient where email = ?1",nativeQuery = true)
    Integer getEmailCount(String email);

    @Modifying
    @Transactional
    @Query(value = "update oda_patient set password = ?1 where id = ?2", nativeQuery = true)
    void updatePassword(String password, Integer adminId);

}
