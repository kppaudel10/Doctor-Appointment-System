package com.oda.service.Impl;

import com.oda.dto.patient.PatientDto;
import com.oda.model.patient.Patient;
import com.oda.repo.patient.PatientRepo;
import com.oda.service.patient.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;

    public PatientServiceImpl(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    @Override
    public PatientDto save(PatientDto patientDto) {
       Patient patient = Patient.builder()
               .id(patientDto.getId())
               .name(patientDto.getName())
               .address(patientDto.getAddress())
               .mobileNumber(patientDto.getMobileNumber())
               .email(patientDto.getEmail())
               .password(patientDto.getPassword()).build();
       //save into database
       Patient patient1 = patientRepo.save(patient);

       return PatientDto.builder().id(patient1.getId()).build();
    }

    @Override
    public PatientDto findById(Integer integer) {
      Patient patient= patientRepo.findById(integer).get();
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .mobileNumber(patient.getMobileNumber()).build();
    }

    @Override
    public List<PatientDto> findALl() {
        return patientRepo.findAll().stream().map(patient -> {
            return PatientDto.builder()
                    .id(patient.getId())
                    .name(patient.getName())
                    .address(patient.getAddress())
                    .mobileNumber(patient.getMobileNumber())
                    .email(patient.getEmail())
                    .password(patient.getPassword()).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer integer) {
    }
}