package com.oda.dto.patient;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
    private Integer id;

    private String name;
    private String address;
    private String mobileNumber;
    private String email;
    private String password;
}
