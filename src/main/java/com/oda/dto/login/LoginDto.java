package com.oda.dto.login;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class LoginDto {
    @NotEmpty(message = "must be enter username")
    String username;

    @NotEmpty(message = "must enter password")
    String password;
}
