package com.oda.model.hospital;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oda_hospital",uniqueConstraints = {
        @UniqueConstraint(name = "unique_hospial_contact" ,columnNames = "contact_number")
})
public class Hospital {
    @Id
    @GeneratedValue(generator = "hospital_sequence",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "hospital_sequence",sequenceName = "hospital_sequence",allocationSize = 13)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "contact_number",nullable = false)
    private String contactNumber;
}
