package com.oda.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAppointmentRequestDto {

    private Integer id;

    private String ppPath;

    private String patientName;

    private String mobileNumber;

    private String address;

    private String doctorName;

    private String date;

    private String fromTime;
}
