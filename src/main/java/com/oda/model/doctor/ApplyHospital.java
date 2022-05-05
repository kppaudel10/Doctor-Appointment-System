package com.oda.model.doctor;

import com.oda.enums.ApplyStatus;
import com.oda.model.hospital.Hospital;
import lombok.*;

import javax.persistence.*;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/30/22
 */

@Entity
@Table(name = "oda_hospital_apply")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(nullable = false)
    private String fromTime;

    @Column(nullable = false)
    private String toTime;

    @Column(nullable = false)
    private String applyDate;

    @Column(nullable = false)
    private ApplyStatus applyStatus;

}
