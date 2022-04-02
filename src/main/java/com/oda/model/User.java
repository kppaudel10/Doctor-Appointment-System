package com.oda.model;

import com.oda.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user",uniqueConstraints = {@UniqueConstraint(name = "user_email",columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(generator = "user_sequence" ,strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_sequence",sequenceName = "user_sequence")
    private Integer id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserStatus userStatus;

}