package com.oda.model.patient;

import com.oda.model.doctor.Doctor;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
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
    @GeneratedValue(generator = "feedback_sequence",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "feedback_sequence",sequenceName = "feedback_sequence")
    private Integer id;

    private String comment;

    @Column(nullable = false)
    private Double rating;

    @Temporal(TemporalType.DATE)
    private Date feedbackDate;

    @OneToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;
}
