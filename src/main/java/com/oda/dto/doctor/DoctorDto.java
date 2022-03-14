package com.oda.dto.doctor;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Integer id;
    private String name;
    private String address;
    private String mobileNumber;
    private String email;
}
