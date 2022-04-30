package com.oda.dto.patient;

import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import lombok.*;

import java.sql.Date;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/17/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Integer id;
    private String comment;
    private Double rating;
    private Doctor doctor;
    private Integer doctorId;
    private Patient patient;
    private Integer star_one;
    private Integer star_two;
    private Integer star_three;
    private Integer star_four;
    private Integer star_five;
    private Date feedbackDate;

}
