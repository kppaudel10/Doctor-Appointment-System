package com.oda.repo.doctor;

import com.oda.model.doctor.DoctorAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailableRepo extends JpaRepository<DoctorAvailable, Integer> {


    @Query(value = "select count(doctor_id)\n" +
            "from doctor_available\n" +
            "where doctor_id = ?1\n" +
            "  and to_char(date, 'yyyy-MM-dd') = ?2 and hospital_id = ?3",nativeQuery = true)
    Integer getCountDoctorAlreadyCheckOrNot(Integer doctorId,String date,Integer hospitalId);


    @Query(value = "select count(doctor_id)\n" +
            "from doctor_available\n" +
            "where doctor_id = ?1\n" +
            "  and to_char(date, 'yyyy-MM-dd') = ?2\n" +
            "  and hospital_id = ?3 and is_available = true",nativeQuery = true)
    Integer checkDoctorAvailability(Integer doctorId,String date,Integer hospitalId);

}
