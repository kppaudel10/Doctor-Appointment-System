package com.oda.repo.doctor;

import com.oda.model.doctor.ApplyHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    @Query(value = "select * from oda_hospital_apply where hospital_id = ?1 and apply_status = 1",nativeQuery = true)
    List<ApplyHospital> findApplyHospitalOfBooked(Integer hospitalId);

    @Query(value = "select * from oda_hospital_apply where hospital_id = ?1 and apply_status = 0",nativeQuery = true)
    List<ApplyHospital> findApplyHospitalOfPending(Integer hospitalId);
}
