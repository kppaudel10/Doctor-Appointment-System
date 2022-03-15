package com.oda.model.patient;

import com.oda.enums.LoginStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "oda_patient",uniqueConstraints = {
        @UniqueConstraint(name = "unique_patient_email",columnNames = "email"),
        @UniqueConstraint(name = "unique_patient_number",columnNames = "mobileNumber")
})
public class Patient {
    @Id
    @GeneratedValue(generator = "patient_sequence",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "patient_sequence",sequenceName = "patient_sequence")
    private Integer id;

    @Column(name = "patient_name",nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
//    private LoginStatus loginStatus;
}
