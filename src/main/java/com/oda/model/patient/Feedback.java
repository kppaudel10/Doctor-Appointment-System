package com.oda.model.patient;

import com.oda.model.doctor.Doctor;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@Entity
@Table(name = "oda_feedback")
public class Feedback {
    @Id
    private Integer id;

    private String comment;

    private Double rating;

    @OneToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
