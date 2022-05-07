package com.oda.dto.doctor;

import com.oda.enums.GenderStatus;
import com.oda.model.patient.Feedback;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Size(max = 30,message = "maximum length")
    private String name;

    @NotEmpty(message = "address must not be empty")
    private String address;

    @NotEmpty(message = "Mobile number must not be empty")
    @Pattern(regexp = "^\\+?(?:977)?[ -]?(?:(?:(?:98|97)-?\\d{8})|(?:01-?\\d{7}))$",
            message = "Invalid number.")
    @Size(max = 10,min = 10 ,message = "Not valid length of number")
    private String mobileNumber;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "Enter valid email")
    private String email;


    @NotEmpty(message = "password must not be empty")
//    @Size(min = 8,message = "Password must of minimum 8 character")
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
//    message = "Password must consist of one uppercase , lowercase ,special character and number")
    private String password;

    @NotEmpty(message = "password must not be empty")
    private String reEnterPassword;

    @NotEmpty(message = "this field must not be empty")
    private String specialization;

    @NotNull(message = "Must enter experience")
    private Double experience;

    private GenderStatus genderStatus;

    private Integer correctPinCode;

    private Integer userPinCode;

    private List<Feedback> feedbackList;

    @NotNull(message = "File must be choose")
    private MultipartFile multipartFilePhoto;

    private String profilePhotoPath;

    private String profilePathWithOutEncode;

//    @NotNull(message = "File must be choose")
//    private MultipartFile multipartFileDocument;

    private Double rating;

    private Integer numberOfFeedback;

    private Integer basicCharge;
}
