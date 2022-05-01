package com.oda.repo.patient;

import com.oda.model.patient.ApplyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Repository
public interface ApplyAppointmentRepo extends JpaRepository<ApplyAppointment,Integer> {

    @Query(value = "select * from oda_appointment where patient_id = ?1",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByPatientId(Integer id);

    @Query(value = "select * from oda_appointment where hospital_id = ?1",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByHospital(Integer hospitalId);
}
