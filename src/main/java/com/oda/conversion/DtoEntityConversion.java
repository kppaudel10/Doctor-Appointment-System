package com.oda.conversion;

import com.oda.dto.patient.PatientDto;
import com.oda.model.patient.Patient;

public class DtoEntityConversion {
    public Patient getPatient(PatientDto patientDto){
        return Patient.builder().
        id(patientDto.getId())
                .name(patientDto.getName())
                .address(patientDto.getAddress())
                .email(patientDto.getEmail())
                .mobileNumber(patientDto.getMobileNumber())
                .password(patientDto.getPassword()).build();
    }
}
