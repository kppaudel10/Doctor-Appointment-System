package com.oda.model.admin;

import com.oda.model.hospital.Hospital;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_admin",uniqueConstraints = {
        @UniqueConstraint(name = "unique_admin_email" ,columnNames = "email"),
        @UniqueConstraint(name = "unique_admin_mobile",columnNames = "mobile_number")
})
public class Admin {
    @Id
    @GeneratedValue(generator = "admin_sequence",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "admin_sequence",sequenceName = "admin_sequence")
    private Integer id;

    @Column(name = "admin_name",nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "mobile_number",nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(nullable = false)
    private String password;

}
