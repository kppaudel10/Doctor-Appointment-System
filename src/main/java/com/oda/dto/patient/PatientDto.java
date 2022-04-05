package com.oda.dto.patient;

import com.oda.enums.GenderStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
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

    @NotEmpty(message = "birthDate must not be empty")
    private String birthDate;

    private MultipartFile multipartFilePP;

    private GenderStatus genderStatus;

    private Integer correctPinCode ;

    private Integer userPinCode;
}
