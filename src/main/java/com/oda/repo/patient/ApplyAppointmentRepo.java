package com.oda.repo.patient;

import com.oda.model.patient.ApplyAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
public interface ApplyAppointmentRepo extends JpaRepository<ApplyAppointment,Integer> {
}
