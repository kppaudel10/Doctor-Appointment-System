package com.oda.dto.patient;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotEmpty(message = "address must not be empty")
    private String address;

    @NotEmpty(message = "contact number must not be empty")
    private String mobileNumber;

    @NotEmpty(message = "email must not be empty")
    private String email;

    @NotEmpty(message = "password must not be empty")
    private String password;

    @NotEmpty(message = "password must not be empty")
    private String reEnterPassword;
}
