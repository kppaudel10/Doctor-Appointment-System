package com.oda.repo.hospital;

import com.oda.model.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/4/22
 */
public interface HospitalRepo extends JpaRepository<Hospital,Integer> {
}
