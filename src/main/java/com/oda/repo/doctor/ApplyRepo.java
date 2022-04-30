package com.oda.repo.doctor;

import com.oda.model.doctor.Apply;
import jdk.dynalink.linker.LinkerServices;
import org.hibernate.validator.constraints.SafeHtml;
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
public interface ApplyRepo extends JpaRepository<Apply,Integer> {

    @Query(value = "select  * from oda_hospital_apply where doctor_id = ?1",nativeQuery = true)
    List<Apply> findApplyByDoctorId(Integer id);
}
