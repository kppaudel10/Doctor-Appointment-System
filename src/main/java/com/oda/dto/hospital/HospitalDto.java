package com.oda.dto.hospital;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalDto {
    private Integer id;
    private String name;
    private String address;
    private String contactNumber;
}
