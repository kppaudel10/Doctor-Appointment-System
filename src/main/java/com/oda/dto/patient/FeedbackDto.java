package com.oda.dto.patient;

import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;
import lombok.*;

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
    private Patient patient;
}
