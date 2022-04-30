package com.oda.model.patient;

import com.oda.enums.ApplyStatus;
import com.oda.model.doctor.Doctor;
import com.oda.model.hospital.Hospital;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_appointment")
public class ApplyAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date applyDate;

    private String fromTime;

    private String toTime;

    private ApplyStatus applyStatus;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "hosipital_id")
    private Hospital hospital;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
