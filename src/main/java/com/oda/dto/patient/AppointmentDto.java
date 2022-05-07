package com.oda.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Integer hospitalApplyId;

    @Size(max = 110)
    private String description;

    private String appointmentDate;
}
