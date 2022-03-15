package com.oda.model.doctor;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_doctor", uniqueConstraints = {
        @UniqueConstraint(name = "unique_doctor_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_doctor_mobile", columnNames = "mobile_number")
})
public class Doctor {
    @Id
    @GeneratedValue(generator = "doctor_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "doctor_sequence", sequenceName = "doctor_sequence", allocationSize = 15)
    private Integer id;

    @Column(name = "doctor_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "mobile_number",nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private Double experience;
}
