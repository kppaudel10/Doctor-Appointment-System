package com.oda.repo.patient;

import com.oda.model.patient.ApplyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
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

    @Query(value = "select * from oda_appointment where hospital_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByHospitalOfPending(Integer hospitalId);

    @Query(value = "select * from oda_appointment where hospital_id = ?1 and apply_status = 1",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByHospitalOfBooked(Integer hospitalId);


    @Query(value = "select * from oda_appointment where doctor_id = ?1 and apply_status = 2",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentBookedOfDoctor(Integer doctorId);

    @Query(value = "select  * from  oda_appointment where apply_date = ?1 and patient_id = ?2 and hospital_id = ?3",nativeQuery = true)
    ApplyAppointment findApplyAppointmentByDateAndPatient(String date, Integer patientId, Integer hospital_id);

    @Query(value = "select * from oda_appointment where patient_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyAppointment> countPendingAppointment(Integer patientId);

    @Query(value = "select * from oda_appointment where patient_id = ?1 and apply_status = 1",nativeQuery = true)
    List<ApplyAppointment> countBookedAppointment(Integer patientId);


    @Query(value = "select * from oda_appointment where doctor_id = ?1 and apply_status = 1 and appointment_date = ?2",nativeQuery = true)
    List<ApplyAppointment> getApplyAppointmentForToday(Integer doctorId,String date);


}
