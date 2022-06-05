package com.oda.dto;

import com.oda.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {
    private Integer userId;
    private UserStatus userStatus;
    private String email;
    private Integer pinCode;
    private Integer userInputPinCode;

    @Size(min = 8,message = "Password must of minimum 8 character")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must consist of one uppercase , lowercase ,special character and number")
    private String newPassword;
}
