package com.oda.model.doctor;

import com.oda.model.hospital.Hospital;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DoctorAvailable")
public class DoctorAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DoctorAvailable_SEQ_GEN")
    @SequenceGenerator(name = "DoctorAvailable_SEQ_GEN", sequenceName = "DoctorAvailable_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date date;


    private Boolean isAvailable;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospitalId;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
