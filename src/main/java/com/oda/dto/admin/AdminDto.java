package com.oda.dto.admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private Integer id;
    private String name;
    private String address;
    private String mobileNumber;
    private String email;
    private String password;
}
