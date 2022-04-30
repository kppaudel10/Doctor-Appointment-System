package com.oda.conversion;

import com.oda.dto.admin.AdminDto;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.PatientDto;
import com.oda.model.admin.Admin;
import com.oda.model.doctor.Doctor;
import com.oda.model.patient.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DtoEntityConversion {
    public Patient getPatient(PatientDto patientDto) throws ParseException {
        return Patient.builder().
        id(patientDto.getId())
                .name(patientDto.getName())
                .address(patientDto.getAddress())
                .email(patientDto.getEmail())
                .genderStatus(patientDto.getGenderStatus())
                .profilePhotoPath(patientDto.getProfilePhotoPath())
                .birthDate(new SimpleDateFormat("yyyy-MM-dd").parse(patientDto.getBirthDate()))
                .mobileNumber(patientDto.getMobileNumber()).build();
    }

    public Doctor getDoctor(DoctorDto doctorDto){
        return Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .address(doctorDto.getAddress())
                .experience(doctorDto.getExperience())
                .email(doctorDto.getEmail())
                .profilePhotoPath(doctorDto.getProfilePhotoPath())
                .password(doctorDto.getPassword())
                .rating(doctorDto.getRating())
                .specialization(doctorDto.getSpecialization())
                .mobileNumber(doctorDto.getMobileNumber()).build();
    }

    public Admin getAdmin(AdminDto adminDto){
        return Admin.builder()
                .id(adminDto.getId())
                .name(adminDto.getName())
                .address(adminDto.getAddress())
                .email(adminDto.getEmail())
                .mobileNumber(adminDto.getMobileNumber()).build();
    }
}
