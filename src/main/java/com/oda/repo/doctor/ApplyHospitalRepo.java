package com.oda.repo.doctor;

import com.oda.model.doctor.ApplyHospital;
import com.oda.model.patient.ApplyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */

@Repository
public interface ApplyHospitalRepo extends JpaRepository<ApplyHospital,Integer> {

    @Query(value = "select  * from oda_hospital_apply where doctor_id = ?1 and apply_status = 1",nativeQuery = true)
    List<ApplyHospital> findApplyByDoctorId(Integer id);

    @Query(value = "select * from oda_hospital_apply  h where h.hospital_id = ?1 and h.apply_status = 1 order by h.id DESC ",nativeQuery = true)
    List<ApplyHospital> findApplyHospitalOfBooked(Integer hospitalId);

    @Query(value = "select * from oda_hospital_apply where hospital_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyHospital> findApplyHospitalOfPending(Integer hospitalId);

    @Query(value = "select * from oda_hospital_apply h where h.doctor_id = ?1 ",nativeQuery = true)
    List<ApplyHospital> findApplyHospitalByDoctorId(Integer id);

    @Query(value = "select * from oda_hospital_apply h where h.doctor_id = ?1 ",nativeQuery = true)
    ApplyHospital findApplyHospitalDetailsByDoctorId(Integer doctorId);

    @Query(value = "select count(id) from oda_hospital_apply h where h.doctor_id = ?1 and h.hospital_id = ?2",nativeQuery = true)
    Integer checkSameDoctorApplyThatHospitalOrNot(Integer doctorId,Integer hospitalId);

    @Query(value = "select * from oda_hospital_apply where apply_date = ?1 and apply_status = 0 and hospital_id = ?2",nativeQuery = true)
    List<ApplyHospital> getTodayDoctorApply(String date,Integer hospitalId);
}
