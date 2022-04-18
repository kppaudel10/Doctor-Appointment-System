package com.oda.repo.patient;

import com.oda.model.patient.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
}
