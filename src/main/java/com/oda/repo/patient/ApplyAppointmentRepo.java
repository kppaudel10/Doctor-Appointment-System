package com.oda.repo.patient;

import com.oda.model.patient.ApplyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Repository
public interface ApplyAppointmentRepo extends JpaRepository<ApplyAppointment,Integer> {

    @Query(value = "select * from oda_appointment a where a.patient_id = ?1  and a.appointment_date >= ?2 order by a.id DESC",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByPatientId(Integer id,String currentDate);

    @Query(value = "select * from oda_appointment where hospital_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByHospitalOfPending(Integer hospitalId);

    @Query(value = "select * from oda_appointment where hospital_id = ?1 and apply_status = 1 order by id DESC ",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByHospitalOfBooked(Integer hospitalId);


    @Query(value = "select * from oda_appointment where doctor_id = ?1 and apply_status = 2",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentBookedOfDoctor(Integer doctorId);

    @Query(nativeQuery = true,value = "select count(id) from oda_appointment where doctor_id = ?1 and apply_status = 2")
    Integer countApplyAppointmentBookedOfDoctor(Integer doctorId);
    @Query(value = "select  * from  oda_appointment where apply_date = ?1 and patient_id = ?2 and hospital_id = ?3",nativeQuery = true)
    ApplyAppointment findApplyAppointmentByDateAndPatient(String date, Integer patientId, Integer hospital_id);

    @Query(value = "select * from oda_appointment where patient_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyAppointment> countPendingAppointment(Integer patientId);

    @Query(value = "select * from oda_appointment where patient_id = ?1 and apply_status = 1",nativeQuery = true)
    List<ApplyAppointment> countBookedAppointment(Integer patientId);


    @Query(value = "select * from oda_appointment where doctor_id = ?1 and apply_status = 1 and appointment_date = ?2",nativeQuery = true)
    List<ApplyAppointment> getApplyAppointmentForToday(Integer doctorId,String date);

    @Query(value = "select * from oda_appointment where apply_date = ?1 and apply_status = 0 and hospital_id = ?2",nativeQuery = true)
    List<ApplyAppointment> getTodayAppointment(String date,Integer hospitalId);

    @Query(value = "select * from oda_appointment where doctor_id = ?1 and patient_id = ?2",nativeQuery = true)
    List<ApplyAppointment> findApplyAppointmentByDoctorIdAndPatientId(Integer doctorId, Integer patientId);

    @Query(value = "select oa.id,\n" +
            "       op.profile_photo_path,\n" +
            "       op.patient_name,\n" +
            "       op.address,\n" +
            "       op.mobile_number,\n" +
            "       od.doctor_name,\n" +
            "       oa.appointment_date,\n" +
            "       oa.from_time\n" +
            "from oda_appointment oa\n" +
            "         inner join oda_patient op on oa.patient_id = op.id\n" +
            "         inner join oda_doctor od on oa.doctor_id = od.id\n" +
            "where hospital_id = ?1\n" +
            "  and apply_status = 1\n" +
            "order by oa.id DESC",nativeQuery = true)
    List<Map<String,Object>> getPatientAppointmentDetails(Integer hospitalId);

    @Query(value = "select count(id)\n" +
            "from oda_appointment\n" +
            "where doctor_id = ?1\n" +
            "  and patient_id = ?2\n" +
            "  and hospital_id = ?3\n" +
            "  and appointment_date = ?4",nativeQuery = true)
    Integer countAppointmentOfDatePatient(Integer doctorId,Integer patientId, Integer hospitalId,String appointmentDate);

}
