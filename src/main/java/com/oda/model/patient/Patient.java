package com.oda.model.patient;

import com.oda.enums.GenderStatus;
import com.oda.enums.LoginStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private GenderStatus genderStatus;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(nullable = false)
    private String password;

    private String profilePhotoPath;


}
