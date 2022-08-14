package com.oda.repo.patient;

import com.oda.model.patient.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

    @Query(value = "select *\n" +
            "from oda_feedback od\n" +
            "where od.doctor_id = ?1\n" +
            "  and length(od.comment) > 0\n" +
            "order by od.feedback_date DESC ",nativeQuery = true)
    List<Feedback> findFeedbackByDoctorId(Integer doctorId);
}
