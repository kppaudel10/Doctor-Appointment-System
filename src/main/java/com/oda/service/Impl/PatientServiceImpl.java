package com.oda.service.Impl;

import com.oda.dto.patient.PatientDto;
import com.oda.enums.UserStatus;
import com.oda.model.User;
import com.oda.model.patient.Patient;
import com.oda.repo.patient.PatientRepo;
import com.oda.service.patient.PatientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public PatientServiceImpl(PatientRepo patientRepo, UserServiceImpl userService, BCryptPasswordEncoder passwordEncoder) {
        this.patientRepo = patientRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public PatientDto save(PatientDto patientDto) throws ParseException {
       Patient patient = Patient.builder()
               .id(patientDto.getId())
               .name(patientDto.getName())
               .address(patientDto.getAddress())
               .mobileNumber(patientDto.getMobileNumber())
               .genderStatus(patientDto.getGenderStatus())
               .birthDate(new SimpleDateFormat("yyyy-MM-dd").parse(patientDto.getBirthDate()))
               .email(patientDto.getEmail()).build();
       //save into database
       Patient patient1 = patientRepo.save(patient);

       //save into database
        User user = new User();
        user.setEmail(patient.getEmail());
        user.setPassword(passwordEncoder.encode(patientDto.getPassword()));
        user.setUserStatus(UserStatus.PATIENT);

        userService.save(user);

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
                    .email(patient.getEmail()).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer integer) {
    }
}
