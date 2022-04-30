package com.oda.repo.doctor;

import com.oda.model.doctor.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */
public interface ApplyRepo extends JpaRepository<Apply,Integer> {
}
