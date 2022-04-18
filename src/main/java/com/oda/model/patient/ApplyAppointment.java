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

    private Date date;

    private Time time;

    private ApplyStatus applyStatus;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "hosipital_id")
    private Hospital hospital;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
